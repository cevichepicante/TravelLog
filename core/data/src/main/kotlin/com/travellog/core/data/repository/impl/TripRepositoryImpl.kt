package com.travellog.core.data.repository.impl

import com.travellog.core.data.mapper.toDomain
import com.travellog.core.model.Page
import com.travellog.core.model.TimelineItem
import com.travellog.core.model.Trip
import com.travellog.core.model.repository.TripRepository
import com.travellog.core.network.api.TripApi
import com.travellog.core.network.dto.trip.CreateTripRequest
import com.travellog.core.network.dto.trip.UpdateTripRequest
import java.time.LocalDate
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val tripApi: TripApi,
) : TripRepository {

    override suspend fun getTrips(query: String?, cursor: String?, limit: Int?): Page<Trip> {
        val page = tripApi.getTrips(query, cursor, limit).data
        return Page(page.items.map { it.toDomain() }, page.nextCursor, page.hasMore)
    }

    override suspend fun getTrip(tripId: String): Trip =
        tripApi.getTrip(tripId).data.toDomain()

    override suspend fun createTrip(title: String, startDate: LocalDate, endDate: LocalDate): Trip =
        tripApi.createTrip(CreateTripRequest(title, startDate.toString(), endDate.toString())).data.toDomain()

    override suspend fun updateTrip(tripId: String, title: String?, startDate: LocalDate?, endDate: LocalDate?): Trip =
        tripApi.updateTrip(tripId, UpdateTripRequest(title, startDate?.toString(), endDate?.toString())).data.toDomain()

    override suspend fun deleteTrip(tripId: String) {
        tripApi.deleteTrip(tripId)
    }

    override suspend fun getTimeline(tripId: String, cursor: String?, limit: Int?): Page<TimelineItem> {
        val page = tripApi.getTimeline(tripId, cursor, limit).data
        return Page(page.items.mapIndexed { index, item -> item.toDomain(order = index) }, page.nextCursor, page.hasMore)
    }
}
