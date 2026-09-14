package com.travellog.core.network.dto.place

import kotlinx.serialization.Serializable

@Serializable
data class PlaceDto(
    val id: String,
    val tripId: String,
    val name: String,
    val category: String? = null,
    val address: String? = null,
    val lat: Double,
    val lng: Double,
    val city: String? = null,
    val country: String? = null,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val emoji: String? = null,
    val accentColor: String? = null,
    val visitedAt: String,
    val createdAt: String,
)

@Serializable
data class PlaceSearchResultDto(
    val name: String,
    val category: String? = null,
    val address: String? = null,
    val lat: Double,
    val lng: Double,
)

@Serializable
data class CreatePlaceRequest(
    val tripId: String,
    val name: String,
    val category: String? = null,
    val address: String? = null,
    val lat: Double,
    val lng: Double,
    val city: String? = null,
    val country: String? = null,
    val visitedAt: String,
    val emoji: String? = null,
)

@Serializable
data class CreatePlaceResponseData(
    val place: PlaceDto,
    val awardedBadgeIds: List<String>,
)

@Serializable
data class UpdatePlaceRequest(
    val emoji: String? = null,
    val visitedAt: String? = null,
)
