package com.johnqualls.reservationapp.ui

import androidx.lifecycle.ViewModel
import com.johnqualls.reservationapp.data.Provider
import com.johnqualls.reservationapp.data.ReservationDataSource
import com.johnqualls.reservationapp.toLocalDate
import com.johnqualls.reservationapp.toMilliseconds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
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

    fun getSchedule(date: Long) {
        val schedule = reservationDataSource.getSchedule(provider.id, date.toLocalDate())
        _uiState.update {
            it.copy(
                selectedDate = date,
                selectedSchedule = schedule
            )
        }
    }
}