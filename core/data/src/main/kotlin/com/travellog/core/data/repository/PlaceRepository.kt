package com.travellog.core.data.repository

import com.travellog.core.model.Place
import com.travellog.core.network.dto.place.PlaceSearchResultDto
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun observePlacesByTrip(tripId: String): Flow<List<Place>>
    suspend fun searchPlaces(query: String, lat: Double? = null, lng: Double? = null): List<PlaceSearchResultDto>
    suspend fun createPlace(
        tripId: String,
        name: String,
        category: String? = null,
        address: String? = null,
        lat: Double,
        lng: Double,
        city: String? = null,
        country: String? = null,
        visitedAt: String,
        emoji: String? = null,
    ): Pair<Place, List<String>>
    suspend fun updatePlace(placeId: String, emoji: String? = null, visitedAt: String? = null): Place
    suspend fun deletePlace(placeId: String)
}
