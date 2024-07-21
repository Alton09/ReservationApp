package com.johnqualls.reservationapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johnqualls.reservationapp.conditional
import com.johnqualls.reservationapp.ui.theme.ReservationAppTheme
import java.time.LocalDate
import java.time.YearMonth


@Preview(showBackground = true)
@Composable
private fun CalendarProviderPreview() {
    ReservationAppTheme {
        Calendar(
            selectedDay =
            LocalDate.now().minusDays(3),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarClientPreview() {
    ReservationAppTheme {
        Calendar(
            enabledDays = setOf(
                LocalDate.now().minusDays(3),
                LocalDate.now().plusDays(2),
                LocalDate.now().minusWeeks(1),
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now().minusDays(3),
                LocalDate.now().plusDays(2),
                LocalDate.now().minusWeeks(1)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentDaySelectedPreview() {
    ReservationAppTheme {
        Calendar(
            selectedDay = LocalDate.now(),
        )
    }
}

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    currentDayDotColor: Color = MaterialTheme.colorScheme.secondary,
    selectedDayBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    enabledDays: Set<LocalDate>? = null,
    selectedDay: LocalDate? = null
) {
    val currentDate = LocalDate.now()
    val yearMonth = YearMonth.now()
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()

    Column(modifier = modifier) {
        DaysOfTheWeekHeader()

        // Days of the month
        val daysInMonth = (1..lastDayOfMonth.dayOfMonth).toList()
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7

        var dayIndex = 0

        for (week in 0..4) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (dayOfWeek in 0..6) {
                    if (week == 0 && dayOfWeek < firstDayOfWeek || dayIndex >= daysInMonth.size) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    } else {
                        val day = daysInMonth[dayIndex]
                        val date = yearMonth.atDay(day)
                        val isEnabled = enabledDays?.contains(date) ?: true

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp)
                                .conditional(selectedDay != null) {
                                    background(
                                        color = if (selectedDay == date) selectedDayBackgroundColor else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (date == currentDate) {
                                Box(
                                    modifier = Modifier
                                        .size(5.dp)
                                        .background(
                                            color = currentDayDotColor,
                                            shape = CircleShape
                                        )
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier.size(30.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = day.toString(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.graphicsLayer {
                                        alpha = if (isEnabled) 1f else 0.5f
                                    }
                                )
                            }
                        }
                        dayIndex++
                    }
                }
            }
        }
    }
}

@Composable
private fun DaysOfTheWeekHeader() {
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}