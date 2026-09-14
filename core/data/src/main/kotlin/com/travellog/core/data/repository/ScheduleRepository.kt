package com.travellog.core.data.repository

import com.travellog.core.model.Destination
import com.travellog.core.model.DestinationSuggestion
import com.travellog.core.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun observeSchedules(): Flow<List<Schedule>>
    suspend fun refreshSchedules()
    suspend fun createSchedule(
        title: String,
        destinations: List<Destination>,
        startDate: String,
        endDate: String,
        color: String? = null,
    ): Schedule
    suspend fun updateSchedule(
        scheduleId: String,
        title: String? = null,
        destinations: List<Destination>? = null,
        startDate: String? = null,
        endDate: String? = null,
        color: String? = null,
    ): Schedule
    suspend fun deleteSchedule(scheduleId: String)
    suspend fun getDestinationSuggestions(city: String): DestinationSuggestion
}
