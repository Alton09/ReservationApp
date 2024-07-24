package com.johnqualls.reservationapp.client.ui

import androidx.lifecycle.ViewModel
import com.johnqualls.reservationapp.core.data.ReservationDataSource
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
        getProviderSchedule()
    }

    private fun getProviderSchedule() {
        val availableProviderDates =
            reservationDataSource.getSchedules(provider.id).map { it.date.toMilliseconds() }
        _uiState.update { it.copy(availableProviderDates = availableProviderDates) }
    }
}