package com.travellog.core.model.repository

import com.travellog.core.model.Destination
import com.travellog.core.model.DestinationSuggestion
import com.travellog.core.model.Schedule
import java.time.LocalDate

interface ScheduleRepository {
    suspend fun getSchedules(): List<Schedule>
    suspend fun getSchedule(scheduleId: String): Schedule
    suspend fun createSchedule(
        title: String,
        destinations: List<Destination>,
        startDate: LocalDate,
        endDate: LocalDate,
        accentColor: Long? = null,
    ): Schedule
    suspend fun updateSchedule(
        scheduleId: String,
        title: String? = null,
        destinations: List<Destination>? = null,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
        accentColor: Long? = null,
    ): Schedule
    suspend fun deleteSchedule(scheduleId: String)
    suspend fun getDestinationSuggestions(city: String): DestinationSuggestion
}
