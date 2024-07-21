package com.johnqualls.reservationapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.johnqualls.reservationapp.R
import com.johnqualls.reservationapp.data.Provider
import com.johnqualls.reservationapp.data.Schedule
import com.johnqualls.reservationapp.ui.theme.ReservationAppTheme
import java.time.LocalDate

@Preview(showBackground = true)
@Composable
private fun ProviderScreenPreview() {
    ReservationAppTheme {
        ProviderScreen()
    }
}

@Composable
fun ProviderScreen(modifier: Modifier = Modifier) {
    val viewModel: ProviderViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    Content(modifier, uiState, viewModel::getSchedule)
}

@Composable
private fun Content(
    modifier: Modifier,
    uiState: ProviderUiState,
    onDateClick: (LocalDate) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        uiState.provider?.let { provider ->
            ProviderDetails(provider)
            ScheduleCalendar(uiState, onDateClick)
            ScheduleShift(uiState.selectedSchedule)
        }
    }
}

@Composable
fun ProviderDetails(provider: Provider) {
    Text(text = "Provider", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = provider.name, style = MaterialTheme.typography.titleMedium)
    Text(text = provider.specialty, style = MaterialTheme.typography.bodyLarge)
}

@Composable
private fun ScheduleCalendar(uiState: ProviderUiState, onDateClick: (LocalDate) -> Unit) {
    Spacer(modifier = Modifier.height(12.dp))
    Text(text = "Schedule", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(8.dp))
    Calendar(
        selectedDay = uiState.selectedDate,
        onDateClick = { onDateClick(it) }
    )
}

@Composable
private fun ScheduleShift(schedule: Schedule?) {
    schedule?.let { schedule ->
        Column {
            TextButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.textButtonColors()
                    .copy(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    text = "Start: 9:00 AM",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            TextButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.textButtonColors()
                    .copy(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    text = "End: 5:00 PM",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    } ?: run {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Add Schedule")
            }
        }
    }
}