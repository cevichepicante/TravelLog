package com.travellog.core.data.repository

import com.travellog.core.model.TimelineItem
import com.travellog.core.model.Trip
import kotlinx.coroutines.flow.Flow

interface TripRepository {
    fun observeTrips(): Flow<List<Trip>>
    suspend fun refreshTrips(query: String? = null)
    suspend fun getTrip(tripId: String): Trip
    suspend fun createTrip(title: String, startDate: String, endDate: String): Trip
    suspend fun updateTrip(tripId: String, title: String? = null, startDate: String? = null, endDate: String? = null): Trip
    suspend fun deleteTrip(tripId: String)
    suspend fun getTimeline(tripId: String, cursor: String? = null, limit: Int? = null): List<TimelineItem>
}
