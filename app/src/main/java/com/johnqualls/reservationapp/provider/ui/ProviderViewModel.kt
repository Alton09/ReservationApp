package com.johnqualls.reservationapp.provider.ui

import androidx.lifecycle.ViewModel
import com.johnqualls.reservationapp.core.data.Provider
import com.johnqualls.reservationapp.core.data.ReservationDataSource
import com.johnqualls.reservationapp.core.to12HourFormat
import com.johnqualls.reservationapp.core.toLocalDate
import com.johnqualls.reservationapp.core.toMilliseconds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ProviderViewModel @Inject constructor(private val reservationDataSource: ReservationDataSource) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ProviderUiState())
    val uiState: StateFlow<ProviderUiState> = _uiState.asStateFlow()

    // TODO Allow user to select provider
    private val provider: Provider = reservationDataSource.getProviders().first()

    init {
        getTodaysSchedule()
    }

    fun getSchedule(date: Long) {
        val schedule = reservationDataSource.getSchedule(provider.id, date.toLocalDate())
        _uiState.update {
            it.copy(
                selectedDate = date,
                scheduleStartTime = schedule?.startTime?.to12HourFormat(),
                scheduleEndTime = schedule?.endTime?.to12HourFormat()
            )
        }
    }

    fun addNewSchedule(scheduleTimes: Pair<LocalTime, LocalTime>) {
        val newSchedule = reservationDataSource.createSchedule(
            providerId = provider.id,
            date = uiState.value.selectedDate.toLocalDate(),
            startTime = scheduleTimes.first,
            endTime = scheduleTimes.second,
        )
        _uiState.update {
            it.copy(
                scheduleStartTime = newSchedule.startTime.to12HourFormat(),
                scheduleEndTime = newSchedule.endTime.to12HourFormat()
            )
        }
    }

    private fun getTodaysSchedule() {
        // TODO Allow choosing of provider
        val todaysDate = LocalDate.now()
        val todaysSchedule = reservationDataSource.getSchedule(provider.id, todaysDate)
        _uiState.update {
            it.copy(
                isLoading = false,
                provider = provider,
                selectedDate = todaysDate.toMilliseconds(),
                scheduleStartTime = todaysSchedule?.startTime?.to12HourFormat(),
                scheduleEndTime = todaysSchedule?.endTime?.to12HourFormat()
            )
        }
    }
}