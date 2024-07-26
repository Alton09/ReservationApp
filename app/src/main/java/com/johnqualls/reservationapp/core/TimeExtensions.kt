package com.johnqualls.reservationapp.core

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("hh:mm a")

fun LocalDate.toMilliseconds(): Long {
    return atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneOffset.UTC).toLocalDate()
}

fun LocalTime.to12HourFormat(): String {
    return format(formatter)
}

fun String.toLocalTime(): LocalTime? {
    return try {
        LocalTime.parse(this, formatter)
    } catch (e: Exception) {
        null
    }
}