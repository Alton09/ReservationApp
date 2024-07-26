package com.johnqualls.reservationapp.core.data

import java.time.LocalDate
import java.time.LocalTime

interface ReservationDataSource {

    fun getClients(): List<Client>

    fun getProviders(): List<Provider>

    fun getSchedules(providerId: String): List<Schedule>

    fun getSchedule(providerId: String, localDate: LocalDate): Schedule?

    fun getReservations(scheduleId: String): List<Reservation>

    fun createSchedule(
        providerId: String,
        date: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime
    ): Schedule

    fun createReservation(
        clientId: String,
        providerId: String,
        scheduleId: String,
        status: ReservationStatus,
        timeSlot: LocalTime
    )
}