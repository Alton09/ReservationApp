package com.johnqualls.reservationapp.client.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.johnqualls.reservationapp.core.data.Client
import com.johnqualls.reservationapp.core.data.Provider
import com.johnqualls.reservationapp.core.toLocalDate
import com.johnqualls.reservationapp.core.toMilliseconds
import java.time.LocalDate

@Composable
fun ClientScreen() {
    val viewModel: ClientViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Content(state, viewModel::getSchedule)
}

@Composable
private fun Content(uiState: ClientUiState, onDateClick: (Long) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        ClientDetails(uiState.client)
        ProviderSchedule(
            provider = uiState.provider,
            availableProviderDates = uiState.availableProviderDates,
            onDateClick = onDateClick
        )
        ProviderScheduleTimeSlots(uiState.selectedScheduleTimeSlots)
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
private fun ProviderScheduleTimeSlots(timeSlots: List<TimeSlot>?) {
    timeSlots?.let {
        LazyRow {
            timeSlots.forEach { timeSlot ->
                item {
                    Button(
                        modifier = Modifier.size(100.dp),
                        onClick = { /*TODO*/ },
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
    }
}
