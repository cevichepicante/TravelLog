package com.travellog.core.model.repository

import com.travellog.core.model.Place
import com.travellog.core.model.PlaceSearchResult
import java.time.Instant

interface PlaceRepository {
    suspend fun searchPlaces(query: String, latitude: Double? = null, longitude: Double? = null): List<PlaceSearchResult>
    suspend fun createPlace(
        tripId: String,
        name: String,
        category: String? = null,
        address: String? = null,
        latitude: Double,
        longitude: Double,
        city: String? = null,
        country: String? = null,
        visitedAt: Instant,
        emoji: String? = null,
    ): Pair<Place, List<String>>
    suspend fun updatePlace(placeId: String, emoji: String? = null, visitedAt: Instant? = null): Place
    suspend fun deletePlace(placeId: String)
}
