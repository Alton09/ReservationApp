package com.johnqualls.reservationapp.ui

import com.johnqualls.reservationapp.data.Schedule

data class ProviderUiState(
    val isLoading: Boolean = true,
    val schedules: List<Schedule> = emptyList(),
)