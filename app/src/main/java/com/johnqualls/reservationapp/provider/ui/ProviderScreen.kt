package com.johnqualls.reservationapp.provider.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.johnqualls.reservationapp.R
import com.johnqualls.reservationapp.core.data.Provider
import com.johnqualls.reservationapp.core.ui.TimePickerDialog
import com.johnqualls.reservationapp.core.ui.theme.ReservationAppTheme
import java.time.LocalTime

@Preview(showBackground = true)
@Composable
private fun ProviderScreenPreview() {
    ReservationAppTheme {
        ProviderScreen()
    }
}

@Composable
fun ProviderScreen() {
    val viewModel: ProviderViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    Content(uiState, viewModel::getSchedule, viewModel::addNewSchedule)
}

@Composable
private fun Content(
    uiState: ProviderUiState,
    onDateClick: (Long) -> Unit,
    onAddNewSchedule: (Pair<LocalTime, LocalTime>) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        uiState.provider?.let { provider ->
            ProviderDetails(provider)
            ScheduleCalendar(uiState, onDateClick)
            ScheduleShift(uiState.scheduleStartTime, uiState.scheduleEndTime, onAddNewSchedule)
        }
    }
}

@Composable
private fun ProviderDetails(provider: Provider) {
    Text(text = "Provider", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = provider.name, style = MaterialTheme.typography.titleMedium)
    Text(text = provider.specialty, style = MaterialTheme.typography.bodyLarge)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleCalendar(uiState: ProviderUiState, onDateClick: (Long) -> Unit) {
    Spacer(modifier = Modifier.height(12.dp))
    Text(text = "Schedule", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(8.dp))
    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = uiState.selectedDate,
        yearRange = IntRange(2024, 2025),
        initialDisplayMode = DisplayMode.Picker
    )
    dateState.selectedDateMillis?.let { onDateClick(it) }
    DatePicker(state = dateState, title = null)
}

@Composable
private fun ScheduleShift(
    startTime: String?,
    endTime: String?,
    onAddNewSchedule: (Pair<LocalTime, LocalTime>) -> Unit
) {
    if (startTime != null && endTime != null) {
        ExistingSchedule(startTime.toString(), endTime.toString())
    } else {
        NewSchedule(onAddNewSchedule)
    }
}

@Composable
private fun ExistingSchedule(startTime: String, endTime: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = "$startTime - $endTime",
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
private fun NewSchedule(onAddNewSchedule: (Pair<LocalTime, LocalTime>) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TOOO move ui state to view model
        var shouldShowDatePicker by remember { mutableStateOf(false) }
        if (shouldShowDatePicker) {
            ScheduleTimePicker(
                onConfirm = {
                    shouldShowDatePicker = false
                    onAddNewSchedule(it)
                },
                onDismiss = {
                    shouldShowDatePicker = false
                }
            )
        } else {
            NoSchedule { shouldShowDatePicker = true }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleTimePicker(onConfirm: (Pair<LocalTime, LocalTime>) -> Unit, onDismiss: () -> Unit) {
    // TOOO move ui state to view model
    var shouldShowFirstPicker by remember { mutableStateOf(true) }
    var startDate: LocalTime? by remember { mutableStateOf(null) }
    var endDate: LocalTime? by remember { mutableStateOf(null) }
    if (shouldShowFirstPicker) {
        TimePickerDialog(
            "Start Time",
            {
                startDate = LocalTime.of(it.hour, it.minute)
                shouldShowFirstPicker = false
            },
            onDismiss = onDismiss
        )
    } else {
        TimePickerDialog(
            "End Time",
            {
                endDate = LocalTime.of(it.hour, it.minute)
                if (startDate != null && endDate != null) {
                    onConfirm(startDate!! to endDate!!)
                }
            },
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun NoSchedule(onAddSchedule: () -> Unit) {
    Image(
        modifier = Modifier.size(85.dp),
        painter = painterResource(id = R.drawable.calendar),
        contentDescription = null,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
    )
    Text(text = "No Schedule", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "There is no schedule for the selected day",
        style = MaterialTheme.typography.bodyLarge
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = onAddSchedule) {
        Text(text = "Add Schedule")
    }
}
