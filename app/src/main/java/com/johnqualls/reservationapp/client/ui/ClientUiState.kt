package com.johnqualls.reservationapp.client.ui

import com.johnqualls.reservationapp.core.data.Client
import com.johnqualls.reservationapp.core.data.Provider
import java.time.LocalDate

data class ClientUiState(
    val client: Client,
    val provider: Provider,
    val availableProviderDates: List<LocalDate>? = null,
    val selectedScheduleTimeSlots: List<TimeSlot>? = null,
    val showReservationTooSoonError: Boolean = false,
    val showReservationDialog: Boolean = false
)

data class TimeSlot(
    val startTime: String,
    val isDisabled: Boolean
)