package com.johnqualls.reservationapp.core

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun LocalDate.toMilliseconds(): Long {
    return atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun LocalTime.to12HourFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    return format(formatter)
}