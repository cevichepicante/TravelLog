package com.travellog.core.data.repository.impl

import com.travellog.core.data.local.dao.ScheduleDao
import com.travellog.core.data.mapper.toDomain
import com.travellog.core.data.mapper.toEntity
import com.travellog.core.data.repository.ScheduleRepository
import com.travellog.core.model.Destination
import com.travellog.core.model.DestinationSuggestion
import com.travellog.core.model.Schedule
import com.travellog.core.network.api.ScheduleApi
import com.travellog.core.network.dto.schedule.CreateScheduleRequest
import com.travellog.core.network.dto.schedule.DestinationDto
import com.travellog.core.network.dto.schedule.UpdateScheduleRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleApi: ScheduleApi,
    private val scheduleDao: ScheduleDao,
) : ScheduleRepository {

    override fun observeSchedules(): Flow<List<Schedule>> =
        scheduleDao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun refreshSchedules() {
        val items = scheduleApi.getSchedules().data.items
        items.forEach { scheduleDao.upsert(it.toEntity()) }
    }

    override suspend fun createSchedule(
        title: String,
        destinations: List<Destination>,
        startDate: String,
        endDate: String,
        color: String?,
    ): Schedule {
        val schedule = scheduleApi.createSchedule(
            CreateScheduleRequest(title, destinations.toDto(), startDate, endDate, color)
        ).data
        scheduleDao.upsert(schedule.toEntity())
        return schedule.toDomain()
    }

    override suspend fun updateSchedule(
        scheduleId: String,
        title: String?,
        destinations: List<Destination>?,
        startDate: String?,
        endDate: String?,
        color: String?,
    ): Schedule {
        val schedule = scheduleApi.updateSchedule(
            scheduleId,
            UpdateScheduleRequest(title, destinations?.toDto(), startDate, endDate, color)
        ).data
        scheduleDao.upsert(schedule.toEntity())
        return schedule.toDomain()
    }

    override suspend fun deleteSchedule(scheduleId: String) {
        scheduleApi.deleteSchedule(scheduleId)
        scheduleDao.deleteById(scheduleId)
    }

    override suspend fun getDestinationSuggestions(city: String): DestinationSuggestion =
        scheduleApi.getDestinationSuggestions(city).data.toDomain()

    private fun List<Destination>.toDto(): List<DestinationDto> =
        map { DestinationDto(it.country, it.city ?: "", it.flag) }
}
