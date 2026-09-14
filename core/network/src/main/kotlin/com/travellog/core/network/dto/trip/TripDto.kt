package com.travellog.core.network.dto.trip

import kotlinx.serialization.Serializable

@Serializable
data class CoverTileDto(val uri: String)

@Serializable
data class TripDto(
    val id: String,
    val title: String,
    val flag: String,
    val country: String,
    val cities: List<String>,
    val startDate: String,
    val endDate: String,
    val photoCnt: Int,
    val placeCnt: Int,
    val badgeCnt: Int,
    val accentColor: String,
    val coverTiles: List<CoverTileDto>,
    val createdAt: String,
    val updatedAt: String,
)

@Serializable
data class CreateTripRequest(
    val title: String,
    val startDate: String,
    val endDate: String,
)

@Serializable
data class UpdateTripRequest(
    val title: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
)

@Serializable
data class TimelineItemDto(
    val type: String,
    val timestamp: String,
    val photo: com.travellog.core.network.dto.photo.PhotoDto? = null,
    val place: com.travellog.core.network.dto.place.PlaceDto? = null,
    val memo: com.travellog.core.network.dto.memo.MemoDto? = null,
)
