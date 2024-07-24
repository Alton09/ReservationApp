package com.johnqualls.reservationapp.data

import java.time.LocalDate

interface ReservationDataSource {

    fun getClients(): List<Client>

    fun getProviders(): List<Provider>

    fun getSchedules(providerId: String): List<Schedule>

    fun getSchedule(providerId: String, localDate: LocalDate): Schedule?

    fun getReservations(): List<Reservation>

    fun createSchedule(schedule: Schedule)
}