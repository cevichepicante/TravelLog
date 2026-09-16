package com.travellog.core.model.repository

import com.travellog.core.model.Page
import com.travellog.core.model.TimelineItem
import com.travellog.core.model.Trip
import java.time.LocalDate

interface TripRepository {
    suspend fun getTrips(query: String? = null, cursor: String? = null, limit: Int? = null): Page<Trip>
    suspend fun getTrip(tripId: String): Trip
    suspend fun createTrip(title: String, startDate: LocalDate, endDate: LocalDate): Trip
    suspend fun updateTrip(
        tripId: String,
        title: String? = null,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
    ): Trip
    suspend fun deleteTrip(tripId: String)
    suspend fun getTimeline(tripId: String, cursor: String? = null, limit: Int? = null): Page<TimelineItem>
}
