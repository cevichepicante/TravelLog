package com.travellog.core.data.repository.impl

import com.travellog.core.data.mapper.toDomain
import com.travellog.core.model.Place
import com.travellog.core.model.PlaceSearchResult
import com.travellog.core.model.repository.PlaceRepository
import com.travellog.core.network.api.PlaceApi
import com.travellog.core.network.dto.place.CreatePlaceRequest
import com.travellog.core.network.dto.place.UpdatePlaceRequest
import java.time.Instant
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeApi: PlaceApi,
) : PlaceRepository {

    override suspend fun searchPlaces(query: String, latitude: Double?, longitude: Double?): List<PlaceSearchResult> =
        placeApi.searchPlaces(query, latitude, longitude).data.items.map { it.toDomain() }

    override suspend fun createPlace(
        tripId: String,
        name: String,
        category: String?,
        address: String?,
        latitude: Double,
        longitude: Double,
        city: String?,
        country: String?,
        visitedAt: Instant,
        emoji: String?,
    ): Pair<Place, List<String>> {
        val response = placeApi.createPlace(
            CreatePlaceRequest(tripId, name, category, address, latitude, longitude, city, country, visitedAt.toString(), emoji)
        ).data
        return Pair(response.place.toDomain(), response.awardedBadgeIds)
    }

    override suspend fun updatePlace(placeId: String, emoji: String?, visitedAt: Instant?): Place =
        placeApi.updatePlace(placeId, UpdatePlaceRequest(emoji, visitedAt?.toString())).data.toDomain()

    override suspend fun deletePlace(placeId: String) {
        placeApi.deletePlace(placeId)
    }
}
