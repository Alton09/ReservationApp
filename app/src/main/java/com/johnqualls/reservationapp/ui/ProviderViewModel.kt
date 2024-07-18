package com.johnqualls.reservationapp.ui

import androidx.lifecycle.ViewModel
import com.johnqualls.reservationapp.data.ReservationDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProviderViewModel @Inject constructor(private val reservationDataSource: ReservationDataSource) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ProviderUiState())
    val uiState: StateFlow<ProviderUiState> = _uiState.asStateFlow()

    init {
        getProvidersSchedules()
    }

    private fun getProvidersSchedules() {
        // TODO allow choosing of provider
        val provider = reservationDataSource.getProviders().first()
        val schedules = reservationDataSource.getSchedules(provider.id)
        _uiState.update { it.copy(isLoading = false, provider = provider, schedules = schedules) }
    }
}