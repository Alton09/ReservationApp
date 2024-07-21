package com.johnqualls.reservationapp

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun LocalDate.toMilliseconds(): Long {
    return atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
}