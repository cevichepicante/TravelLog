package com.travellog.core.data.repository.impl

import com.travellog.core.data.local.dao.TripDao
import com.travellog.core.data.mapper.toDomain
import com.travellog.core.data.mapper.toEntity
import com.travellog.core.data.repository.TripRepository
import com.travellog.core.model.TimelineItem
import com.travellog.core.model.Trip
import com.travellog.core.network.api.TripApi
import com.travellog.core.network.dto.trip.CreateTripRequest
import com.travellog.core.network.dto.trip.UpdateTripRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val tripApi: TripApi,
    private val tripDao: TripDao,
) : TripRepository {

    override fun observeTrips(): Flow<List<Trip>> =
        tripDao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun refreshTrips(query: String?) {
        val items = tripApi.getTrips(query = query).data.items
        tripDao.upsertAll(items.map { it.toEntity() })
    }

    override suspend fun getTrip(tripId: String): Trip {
        val cached = tripDao.getById(tripId).first()
        if (cached != null) return cached.toDomain()
        return tripApi.getTrip(tripId).data.toDomain()
    }

    override suspend fun createTrip(title: String, startDate: String, endDate: String): Trip {
        val trip = tripApi.createTrip(CreateTripRequest(title, startDate, endDate)).data
        tripDao.upsert(trip.toEntity())
        return trip.toDomain()
    }

    override suspend fun updateTrip(
        tripId: String,
        title: String?,
        startDate: String?,
        endDate: String?,
    ): Trip {
        val trip = tripApi.updateTrip(tripId, UpdateTripRequest(title, startDate, endDate)).data
        tripDao.upsert(trip.toEntity())
        return trip.toDomain()
    }

    override suspend fun deleteTrip(tripId: String) {
        tripApi.deleteTrip(tripId)
        tripDao.deleteById(tripId)
    }

    override suspend fun getTimeline(tripId: String, cursor: String?, limit: Int?): List<TimelineItem> =
        tripApi.getTimeline(tripId, cursor, limit).data.items.mapNotNull { item ->
            val timestamp = Instant.parse(item.timestamp)
            when (item.type) {
                "PHOTO" -> item.photo?.toDomain()?.let { TimelineItem.PhotoItem(timestamp, it) }
                "PLACE" -> item.place?.toDomain()?.let { TimelineItem.PlaceItem(timestamp, it) }
                "MEMO" -> item.memo?.toDomain()?.let { TimelineItem.MemoItem(timestamp, it) }
                else -> null
            }
        }
}
