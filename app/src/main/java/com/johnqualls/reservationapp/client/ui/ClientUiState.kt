package com.johnqualls.reservationapp.client.ui

import com.johnqualls.reservationapp.core.data.Client
import com.johnqualls.reservationapp.core.data.Provider
import java.time.LocalDate

data class ClientUiState(
    val client: Client,
    val provider: Provider,
    val selectedDate: Long = System.currentTimeMillis(),
    val availableProviderDates: List<LocalDate>? = null,
)