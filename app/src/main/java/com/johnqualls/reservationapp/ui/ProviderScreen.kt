package com.johnqualls.reservationapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.johnqualls.reservationapp.data.Provider
import com.johnqualls.reservationapp.ui.theme.ReservationAppTheme

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
    Content(modifier, uiState)
}

@Composable
private fun Content(modifier: Modifier, uiState: ProviderUiState) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        uiState.provider?.let { provider ->
            ProviderDetails(provider)
            ScheduleCalendar(uiState)
            ScheduleShift()
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
private fun ScheduleCalendar(uiState: ProviderUiState) {
    Spacer(modifier = Modifier.height(12.dp))
    Text(text = "Schedule", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(8.dp))
    Calendar(
        selectedDays = uiState.schedules.map { it.date }.toSet()
    )
}

@Composable
private fun ScheduleShift(modifier: Modifier = Modifier) {
    Column {
        Text("Shift", style = MaterialTheme.typography.headlineSmall)
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Start: 9:00 AM", style = MaterialTheme.typography.titleMedium)
        }
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "End: 5:00 PM", style = MaterialTheme.typography.titleMedium)
        }
    }
}