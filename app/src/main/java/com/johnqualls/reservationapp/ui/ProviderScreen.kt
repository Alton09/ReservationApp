package com.johnqualls.reservationapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    Column {
        Calendar()

        Schedule()
    }
}

@Composable
fun Schedule(modifier: Modifier = Modifier) {
    Column {
        Text(text = "Schedule", style = MaterialTheme.typography.headlineSmall)
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "9:00 AM")
        }
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "5:00 PM")
        }
    }
}