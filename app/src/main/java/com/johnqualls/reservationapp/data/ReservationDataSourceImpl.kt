package com.johnqualls.reservationapp.data

import java.time.LocalDate
import java.util.UUID

class ReservationDataSourceImpl : ReservationDataSource {

    private val clients = mutableListOf(
        Client(
            id = UUID.randomUUID().toString(),
            name = "John Doe",
            email = "john.doe@example.com"
        ),
        Client(
            id = UUID.randomUUID().toString(),
            name = "Jane Smith",
            email = "jane.smith@example.com"
        ),
        Client(
            id = UUID.randomUUID().toString(),
            name = "Alice Johnson",
            email = "alice.johnson@example.com"
        )
    )


    private val providers = mutableListOf(
        Provider(
            id = UUID.randomUUID().toString(),
            name = "Dr. Joyce Wrice",
            specialty = "Neurology",
            experience = 10,
            rating = 4.8f
        ),
        Provider(
            id = UUID.randomUUID().toString(),
            name = "Dr. Mark Lee",
            specialty = "Cardiology",
            experience = 15,
            rating = 4.6f
        ),
        Provider(
            id = UUID.randomUUID().toString(),
            name = "Dr. Emma Davis",
            specialty = "Dermatology",
            experience = 8,
            rating = 4.9f
        )
    )

    private val schedules = mutableListOf(
        Schedule(
            id = UUID.randomUUID().toString(),
            providerId = providers[0].id,
            date = LocalDate.of(2024, 7, 22),
            startTime = "08:00",
            endTime = "09:00"
        ),
        Schedule(
            id = UUID.randomUUID().toString(),
            providerId = providers[0].id,
            date = LocalDate.of(2024, 7, 23),
            startTime = "10:00",
            endTime = "11:00"
        ),
        Schedule(
            id = UUID.randomUUID().toString(),
            providerId = providers[1].id,
            date = LocalDate.of(2024, 7, 18),
            startTime = "09:00",
            endTime = "10:00"
        ),
        Schedule(
            id = UUID.randomUUID().toString(),
            providerId = providers[2].id,
            date = LocalDate.of(2024, 7, 19),
            startTime = "14:00",
            endTime = "15:00"
        )
    )

    private val reservations = mutableListOf(
        Reservation(
            id = UUID.randomUUID().toString(),
            clientId = clients[0].id,
            providerId = providers[0].id,
            scheduleId = schedules[0].id,
            status = "confirmed",
            timeSlot = "08:00-09:00"
        ),
        Reservation(
            id = UUID.randomUUID().toString(),
            clientId = clients[1].id,
            providerId = providers[1].id,
            scheduleId = schedules[2].id,
            status = "pending",
            timeSlot = "09:00-10:00"
        ),
        Reservation(
            id = UUID.randomUUID().toString(),
            clientId = clients[2].id,
            providerId = providers[2].id,
            scheduleId = schedules[3].id,
            status = "canceled",
            timeSlot = "14:00-15:00"
        )
    )

    override fun getClients(): List<Client> {
        return clients
    }

    override fun getProviders(): List<Provider> {
        return providers
    }

    override fun getSchedules(providerId: String): List<Schedule> {
        return schedules.filter { it.providerId == providerId }
    }

    override fun getReservations(): List<Reservation> {
        return reservations
    }
}
