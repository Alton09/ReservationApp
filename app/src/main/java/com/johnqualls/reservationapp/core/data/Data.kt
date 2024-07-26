package com.johnqualls.reservationapp.core.data

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class Provider(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val specialty: String,
    val experience: Int,
    val rating: Float
)

data class Client(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String
)

data class Schedule(
    val id: String = UUID.randomUUID().toString(),
    val providerId: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val timeSlots: List<LocalTime>
)

data class Reservation(
    val id: String = UUID.randomUUID().toString(),
    val clientId: String,
    val providerId: String,
    val scheduleId: String,
    val status: ReservationStatus,
    val timeSlot: LocalTime
)

enum class ReservationStatus {
    RESERVED,
    CONFIRMED
}

