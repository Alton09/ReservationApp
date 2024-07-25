package com.johnqualls.reservationapp.core.data

import com.johnqualls.reservationapp.core.data.ReservationStatus.CONFIRMED
import java.time.LocalDate
import java.time.LocalTime

class ReservationDataSourceImpl : ReservationDataSource {

    private val clients = mutableListOf(
        Client(
            name = "John Doe",
            email = "john.doe@example.com"
        ),
        Client(
            name = "Jane Smith",
            email = "jane.smith@example.com"
        ),
        Client(
            name = "Alice Johnson",
            email = "alice.johnson@example.com"
        )
    )


    private val providers = mutableListOf(
        Provider(
            name = "Dr. Joyce Wrice",
            specialty = "Neurology",
            experience = 10,
            rating = 4.8f
        ),
        Provider(
            name = "Dr. Mark Lee",
            specialty = "Cardiology",
            experience = 15,
            rating = 4.6f
        ),
        Provider(
            name = "Dr. Emma Davis",
            specialty = "Dermatology",
            experience = 8,
            rating = 4.9f
        )
    )

    private val schedules = mutableListOf(
        Schedule(
            providerId = providers[0].id,
            date = LocalDate.of(2024, 7, 22),
            startTime = LocalTime.of(8, 0),
            endTime = LocalTime.of(9, 0)
        ),
        Schedule(
            providerId = providers[0].id,
            date = LocalDate.of(2024, 7, 23),
            startTime = LocalTime.of(10, 0),
            endTime = LocalTime.of(11, 0)
        ),
        Schedule(
            providerId = providers[1].id,
            date = LocalDate.of(2024, 7, 18),
            startTime = LocalTime.of(9, 0),
            endTime = LocalTime.of(10, 0)
        ),
        Schedule(
            providerId = providers[2].id,
            date = LocalDate.of(2024, 7, 19),
            startTime = LocalTime.of(14, 0),
            endTime = LocalTime.of(15, 0)
        )
    )

    private val reservations = mutableListOf(
        Reservation(
            clientId = clients[0].id,
            providerId = providers[0].id,
            scheduleId = schedules[0].id,
            status = CONFIRMED,
            timeSlot = LocalTime.of(8, 0)
        ),
        Reservation(
            clientId = clients[1].id,
            providerId = providers[1].id,
            scheduleId = schedules[2].id,
            status = CONFIRMED,
            timeSlot = LocalTime.of(9, 30)
        ),
        Reservation(
            clientId = clients[2].id,
            providerId = providers[2].id,
            scheduleId = schedules[3].id,
            status = CONFIRMED,
            timeSlot = LocalTime.of(14, 15)
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

    override fun getSchedule(providerId: String, localDate: LocalDate): Schedule? {
        return schedules.find { it.providerId == providerId && it.date == localDate }
    }

    override fun getReservations(scheduleId: String): List<Reservation> {
        return reservations.filter { it.scheduleId == scheduleId }
    }

    override fun createSchedule(schedule: Schedule) {
        schedules.add(
            schedule
        )
    }
}
