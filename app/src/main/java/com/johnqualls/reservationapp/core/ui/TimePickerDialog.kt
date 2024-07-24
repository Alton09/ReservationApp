package com.johnqualls.reservationapp.core.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    title: String,
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {

    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
    )

    TimePickerDialogStateless(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState) },
        title = title,
        {
            TimePicker(
                state = timePickerState,
            )
        },
    )
}

@Composable
private fun TimePickerDialogStateless(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    content: @Composable () -> Unit
) {
    AlertDialog(
        title = { Text(title) },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}