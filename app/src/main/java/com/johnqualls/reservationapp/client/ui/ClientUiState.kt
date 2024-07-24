package com.johnqualls.reservationapp.client.ui

import com.johnqualls.reservationapp.core.data.Client
import com.johnqualls.reservationapp.core.data.Provider
import com.johnqualls.reservationapp.core.data.Schedule

data class ClientUiState(
    val client: Client,
    val provider: Provider,
    val availableProviderDates: List<Long>? = null,
    val selectedSchedule: Schedule? = null
)