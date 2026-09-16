package com.travellog.core.data.repository.impl

import com.travellog.core.data.mapper.toDomain
import com.travellog.core.data.mapper.toDto
import com.travellog.core.data.mapper.toHexColor
import com.travellog.core.model.Destination
import com.travellog.core.model.DestinationSuggestion
import com.travellog.core.model.Schedule
import com.travellog.core.model.repository.ScheduleRepository
import com.travellog.core.network.api.ScheduleApi
import com.travellog.core.network.dto.schedule.CreateScheduleRequest
import com.travellog.core.network.dto.schedule.UpdateScheduleRequest
import java.time.LocalDate
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleApi: ScheduleApi,
) : ScheduleRepository {

    override suspend fun getSchedules(): List<Schedule> =
        scheduleApi.getSchedules().data.items.map { it.toDomain() }

    override suspend fun getSchedule(scheduleId: String): Schedule =
        scheduleApi.getSchedule(scheduleId).data.toDomain()

    override suspend fun createSchedule(
        title: String,
        destinations: List<Destination>,
        startDate: LocalDate,
        endDate: LocalDate,
        accentColor: Long?,
    ): Schedule =
        scheduleApi.createSchedule(
            CreateScheduleRequest(title, destinations.map { it.toDto() }, startDate.toString(), endDate.toString(), accentColor?.toHexColor())
        ).data.toDomain()

    override suspend fun updateSchedule(
        scheduleId: String,
        title: String?,
        destinations: List<Destination>?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        accentColor: Long?,
    ): Schedule =
        scheduleApi.updateSchedule(
            scheduleId,
            UpdateScheduleRequest(
                title, destinations?.map { it.toDto() }, startDate?.toString(), endDate?.toString(), accentColor?.toHexColor(),
            ),
        ).data.toDomain()

    override suspend fun deleteSchedule(scheduleId: String) {
        scheduleApi.deleteSchedule(scheduleId)
    }

    override suspend fun getDestinationSuggestions(city: String): DestinationSuggestion =
        scheduleApi.getDestinationSuggestions(city).data.toDomain()
}
