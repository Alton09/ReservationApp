package com.johnqualls.reservationapp.ui

import androidx.lifecycle.ViewModel
import com.johnqualls.reservationapp.data.Provider
import com.johnqualls.reservationapp.data.ReservationDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class ProviderViewModel @Inject constructor(private val reservationDataSource: ReservationDataSource) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ProviderUiState())
    val uiState: StateFlow<ProviderUiState> = _uiState.asStateFlow()

    private val provider: Provider = reservationDataSource.getProviders().first()

    init {
        getTodaysSchedule()
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
                selectedSchedule = todaysSchedule
            )
        }
    }

    fun getSchedule(date: LocalDate) {
        val schedule = reservationDataSource.getSchedule(provider.id, date)
        _uiState.update {
            it.copy(
                selectedDate = date.toMilliseconds(),
                selectedSchedule = schedule
            )
        }
    }

    private fun LocalDate.toMilliseconds(): Long {
        return atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}