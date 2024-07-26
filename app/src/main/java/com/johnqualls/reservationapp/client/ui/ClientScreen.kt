package com.johnqualls.reservationapp.client.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.johnqualls.reservationapp.core.data.Client
import com.johnqualls.reservationapp.core.data.Provider
import com.johnqualls.reservationapp.core.data.ReservationStatus
import com.johnqualls.reservationapp.core.toLocalDate
import com.johnqualls.reservationapp.core.toMilliseconds
import java.time.LocalDate

@Composable
fun ClientScreen() {
    val viewModel: ClientViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Content(
        state,
        viewModel::getSchedule,
        { viewModel.reserve(it, ReservationStatus.PENDING) },
        viewModel::confirmReservation
    )
}

@Composable
private fun Content(
    uiState: ClientUiState, onDateClick: (Long) -> Unit,
    onReserve: (TimeSlot) -> Unit,
    onConfirm: (TimeSlot) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        ClientDetails(uiState.client)
        ProviderSchedule(
            provider = uiState.provider,
            availableProviderDates = uiState.availableProviderDates,
            onDateClick = onDateClick
        )
        Reservations(uiState.selectedScheduleTimeSlots, onReserve, onConfirm)
    }
}

@Composable
private fun ClientDetails(client: Client) {
    Text(text = "Client", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = client.name, style = MaterialTheme.typography.titleMedium)
    Text(text = client.email, style = MaterialTheme.typography.bodyLarge)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProviderSchedule(
    provider: Provider,
    availableProviderDates: List<LocalDate>?,
    onDateClick: (Long) -> Unit
) {
    availableProviderDates?.let {
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Provider Schedule", style = MaterialTheme.typography.headlineSmall)
        Text(text = provider.name, style = MaterialTheme.typography.titleMedium)
        // TOOO move date picker to reusable composable
        Spacer(modifier = Modifier.height(8.dp))
        val dateState = rememberDatePickerState(
            initialSelectedDateMillis = availableProviderDates.first().toMilliseconds(),
            yearRange = IntRange(2024, 2025),
            initialDisplayMode = DisplayMode.Picker,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return availableProviderDates.contains(utcTimeMillis.toLocalDate())
                }
            }
        )
        dateState.selectedDateMillis?.let { onDateClick(it) }
        DatePicker(state = dateState, title = null)
    }
}

@Composable
private fun Reservations(
    timeSlots: List<TimeSlot>?,
    onReserve: (TimeSlot) -> Unit,
    onConfirm: (TimeSlot) -> Unit
) {
    timeSlots?.let {
        // TODO Move dialog state management to viewmodel
        var shouldShowReservationDialog by remember { mutableStateOf(false) }
        var selectedTimeSlot: TimeSlot? by remember { mutableStateOf(null) }
        LazyRow {
            timeSlots.forEach { timeSlot ->
                item {
                    Button(
                        modifier = Modifier.size(100.dp),
                        onClick = {
                            shouldShowReservationDialog = true
                            selectedTimeSlot = timeSlot
                        },
                        enabled = !timeSlot.isReserved,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = timeSlot.startTime)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
        if (shouldShowReservationDialog && selectedTimeSlot != null) {
            ReservationDialog({
                shouldShowReservationDialog = false
                onReserve(selectedTimeSlot!!)
            }, {
                shouldShowReservationDialog = false
                onConfirm(selectedTimeSlot!!)
            })
        }
    }
}

@Composable
private fun ReservationDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        title = { Text(text = "Reservation") },
        text = { Text(text = "This appointment has been reserved and will expire in thirty minutes. Would you like to confirm to book the appointment?") },
        dismissButton = {
            Text(modifier = Modifier.clickable { onDismiss() }, text = "Dismiss")
        },
        confirmButton = {
            Text(modifier = Modifier.clickable { onConfirm() }, text = "Confirm")
        },
        onDismissRequest = onDismiss,
    )
}
