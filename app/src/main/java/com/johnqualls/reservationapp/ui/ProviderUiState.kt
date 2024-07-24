package com.johnqualls.reservationapp.ui

import com.johnqualls.reservationapp.data.Provider

data class ProviderUiState(
    val isLoading: Boolean = true,
    val provider: Provider? = null,
    val selectedDate: Long = System.currentTimeMillis(),
    val scheduleStartTime: String? = null,
    val scheduleEndTime: String? = null,
)