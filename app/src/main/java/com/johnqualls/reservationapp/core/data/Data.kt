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
) {
    override fun toString(): String {
        return """
            Reservation(
                clientId='$clientId',
                providerId='$providerId',
                scheduleId='$scheduleId',
                status=$status,
                timeSlot=$timeSlot
            )"""
    }

    /*
     Overriding equals and hashcode to make replacing reservations in a data structure easier
     Uniqueness is determined by scheduleId and timeSlot
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Reservation

        if (scheduleId != other.scheduleId) return false
        if (timeSlot != other.timeSlot) return false

        return true
    }

    override fun hashCode(): Int {
        var result = scheduleId.hashCode()
        result = 31 * result + timeSlot.hashCode()
        return result
    }
}

enum class ReservationStatus {
    RESERVED,
    CONFIRMED
}

