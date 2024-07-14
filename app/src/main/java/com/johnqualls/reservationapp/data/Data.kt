package com.johnqualls.reservationapp.data

import java.time.LocalDate

data class Provider(
    val id: String,
    val name: String,
    val specialty: String,
    val experience: Int,
    val rating: Float
)

data class Client(
    val id: String,
    val name: String,
    val email: String
)

data class Schedule(
    val id: String,
    val providerId: String,
    val date: LocalDate,
    val startTime: String,
    val endTime: String
)

data class Reservation(
    val id: String,
    val clientId: String,
    val providerId: String,
    val scheduleId: String,
    val status: String, // e.g., "pending", "confirmed", "canceled"
    val timeSlot: String
)

