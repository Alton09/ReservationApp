package com.johnqualls.reservationapp.client.ui

import androidx.lifecycle.ViewModel
import com.johnqualls.reservationapp.core.data.Reservation
import com.johnqualls.reservationapp.core.data.ReservationDataSource
import com.johnqualls.reservationapp.core.data.ReservationStatus
import com.johnqualls.reservationapp.core.data.ReservationStatus.CONFIRMED
import com.johnqualls.reservationapp.core.data.ReservationStatus.RESERVED
import com.johnqualls.reservationapp.core.data.Schedule
import com.johnqualls.reservationapp.core.to12HourFormat
import com.johnqualls.reservationapp.core.toLocalDate
import com.johnqualls.reservationapp.core.toLocalTime
import com.johnqualls.reservationapp.core.toMilliseconds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(private val reservationDataSource: ReservationDataSource) :
    ViewModel() {

    // TODO Allow user to select client
    private val client = reservationDataSource.getClients().first()

    // TODO Allow user to select provider
    private val provider = reservationDataSource.getProviders().first()

    private val _uiState = MutableStateFlow(ClientUiState(client = client, provider = provider))
    val uiState: StateFlow<ClientUiState> = _uiState.asStateFlow()

    private var selectedSchedule: Schedule? = null


    init {
        getProviderSchedules()
    }

    fun getSchedule(date: LocalDate) {
        reservationDataSource.getSchedule(provider.id, date)?.let { schedule ->
            selectedSchedule = schedule
            val reservations = reservationDataSource.getReservations(schedule.id)
            val timeSlots = mapReservedTimeSlots(schedule, reservations)
            _uiState.update {
                it.copy(
                    selectedScheduleTimeSlots = timeSlots
                )
            }
        }
    }

    fun reserve(timeSlot: TimeSlot, reservationStatus: ReservationStatus) {
        val ignoreReservation = ignoreIfAlreadyReserved(timeSlot, reservationStatus)
        if (ignoreReservation) return

        // Create reservation
        selectedSchedule?.let { schedule ->
            timeSlot.startTime.toLocalTime()?.let { time ->
                reservationDataSource.createReservation(
                    client.id,
                    provider.id,
                    schedule.id,
                    reservationStatus,
                    time
                )

                // Refresh schedule
                getSchedule(schedule.date)
            }
        }
    }

    fun confirmReservation(timeSlot: TimeSlot) {
        reserve(timeSlot, CONFIRMED)
    }

    private fun getProviderSchedules() {
        val availableProviderDates =
            reservationDataSource.getSchedules(provider.id)
                .map { it.date.toMilliseconds().toLocalDate() }
        _uiState.update { it.copy(availableProviderDates = availableProviderDates) }
    }

    private fun mapReservedTimeSlots(
        schedule: Schedule,
        reservations: List<Reservation>
    ): List<TimeSlot> {
        var reservation: Reservation?
        return schedule.timeSlots.map { timeSlot ->
            reservation = reservations.find { it.timeSlot == timeSlot }
            TimeSlot(
                timeSlot.to12HourFormat(),
                isReservationLocked(reservation),
            )
        }
    }

    private fun isReservationLocked(reservation: Reservation?) =
        reservation?.status == CONFIRMED || (reservation?.status == RESERVED && reservation.clientId != client.id)

    private fun ignoreIfAlreadyReserved(
        timeSlot: TimeSlot,
        reservationStatus: ReservationStatus
    ): Boolean {
        val localTimeSlot = timeSlot.startTime.toLocalTime()
        val reservations = reservationDataSource.getReservations(selectedSchedule!!.id)
        val existingReservation = reservations.find { it.timeSlot == localTimeSlot }
        if (existingReservation != null) {
            if (existingReservation.status == RESERVED && reservationStatus == RESERVED) {
                return true
            }
        }
        return false
    }
}