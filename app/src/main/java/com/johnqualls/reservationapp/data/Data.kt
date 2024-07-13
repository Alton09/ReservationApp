package com.johnqualls.reservationapp.data

import java.time.LocalDate
import java.util.UUID

data class Provider(
    val id: UUID,
    val name: String,
    val specialty: String,
    val experience: Int,
    val rating: Float
)

data class Client(
    val id: UUID,
    val name: String,
    val email: String
)

data class Schedule(
    val id: UUID,
    val providerId: UUID,
    val date: LocalDate,
    val startTime: String,
    val endTime: String
)

data class Reservation(
    val id: UUID,
    val clientId: UUID,
    val providerId: UUID,
    val scheduleId: UUID,
    val status: String, // e.g., "pending", "confirmed", "canceled"
    val timeSlot: String
)

