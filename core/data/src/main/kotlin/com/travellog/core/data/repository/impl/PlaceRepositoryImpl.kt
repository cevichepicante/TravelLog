package com.travellog.core.data.repository.impl

import com.travellog.core.data.local.dao.PlaceDao
import com.travellog.core.data.mapper.toDomain
import com.travellog.core.data.mapper.toEntity
import com.travellog.core.data.repository.PlaceRepository
import com.travellog.core.model.Place
import com.travellog.core.network.api.PlaceApi
import com.travellog.core.network.dto.place.CreatePlaceRequest
import com.travellog.core.network.dto.place.PlaceSearchResultDto
import com.travellog.core.network.dto.place.UpdatePlaceRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeApi: PlaceApi,
    private val placeDao: PlaceDao,
) : PlaceRepository {

    override fun observePlacesByTrip(tripId: String): Flow<List<Place>> =
        placeDao.getByTrip(tripId).map { list -> list.map { it.toDomain() } }

    override suspend fun searchPlaces(query: String, lat: Double?, lng: Double?): List<PlaceSearchResultDto> =
        placeApi.searchPlaces(query, lat, lng).data.items

    override suspend fun createPlace(
        tripId: String,
        name: String,
        category: String?,
        address: String?,
        lat: Double,
        lng: Double,
        city: String?,
        country: String?,
        visitedAt: String,
        emoji: String?,
    ): Pair<Place, List<String>> {
        val response = placeApi.createPlace(
            CreatePlaceRequest(tripId, name, category, address, lat, lng, city, country, visitedAt, emoji)
        ).data
        placeDao.upsert(response.place.toEntity())
        return Pair(response.place.toDomain(), response.awardedBadgeIds)
    }

    override suspend fun updatePlace(placeId: String, emoji: String?, visitedAt: String?): Place {
        val place = placeApi.updatePlace(placeId, UpdatePlaceRequest(emoji, visitedAt)).data
        placeDao.upsert(place.toEntity())
        return place.toDomain()
    }

    override suspend fun deletePlace(placeId: String) {
        placeApi.deletePlace(placeId)
        placeDao.deleteById(placeId)
    }
}
