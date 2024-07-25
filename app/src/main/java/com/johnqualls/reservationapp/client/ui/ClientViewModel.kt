package com.johnqualls.reservationapp.client.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.johnqualls.reservationapp.core.data.Reservation
import com.johnqualls.reservationapp.core.data.ReservationDataSource
import com.johnqualls.reservationapp.core.data.Schedule
import com.johnqualls.reservationapp.core.to12HourFormat
import com.johnqualls.reservationapp.core.toLocalDate
import com.johnqualls.reservationapp.core.toMilliseconds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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


    init {
        getProviderSchedules()
    }

    private fun getProviderSchedules() {
        val availableProviderDates =
            reservationDataSource.getSchedules(provider.id)
                .map { it.date.toMilliseconds().toLocalDate() }
        _uiState.update { it.copy(availableProviderDates = availableProviderDates) }
    }

    fun getSchedule(date: Long) {
        reservationDataSource.getSchedule(provider.id, date.toLocalDate())?.let { schedule ->
            val reservations = reservationDataSource.getReservations(schedule.id)
            val timeSlots = mapReservedTimeSlots(schedule, reservations)
            Log.d("JAQ", timeSlots.toString())
            _uiState.update {
                it.copy(
                    selectedScheduleSlots = timeSlots
                )
            }
        }
    }

    private fun mapReservedTimeSlots(
        schedule: Schedule,
        reservations: List<Reservation>
    ): List<TimeSlot> {
        var isReserved: Boolean
        return schedule.timeSlots.map { timeSlot ->
            isReserved = reservations.any { it.timeSlot == timeSlot }
            TimeSlot(timeSlot.to12HourFormat(), isReserved)
        }
    }
}