package com.johnqualls.reservationapp.data

interface ReservationDataSource {

    fun getClients(): List<Client>

    fun getProviders(): List<Provider>

    fun getSchedules(): List<Schedule>

    fun getReservations(): List<Reservation>
}