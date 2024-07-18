package com.johnqualls.reservationapp.ui

import com.johnqualls.reservationapp.data.Provider
import com.johnqualls.reservationapp.data.Schedule

data class ProviderUiState(
    val isLoading: Boolean = true,
    val provider: Provider? = null,
    val selectedSchedule: Schedule? = null
)