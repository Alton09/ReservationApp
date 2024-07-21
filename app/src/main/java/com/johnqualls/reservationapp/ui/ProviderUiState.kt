package com.johnqualls.reservationapp.ui

import com.johnqualls.reservationapp.data.Provider
import com.johnqualls.reservationapp.data.Schedule
import java.time.LocalDate

data class ProviderUiState(
    val isLoading: Boolean = true,
    val provider: Provider? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedSchedule: Schedule? = null
)