package com.travellog.core.data.mapper

import com.travellog.core.data.local.entity.PlaceEntity
import com.travellog.core.model.Place
import com.travellog.core.network.dto.place.PlaceDto
import java.time.Instant

fun PlaceDto.toEntity(): PlaceEntity = PlaceEntity(
    id = id,
    tripId = tripId,
    name = name,
    category = category ?: "",
    address = address ?: "",
    latitude = lat,
    longitude = lng,
    city = city ?: "",
    country = country ?: "",
    rating = rating?.toFloat() ?: 0f,
    reviewCount = reviewCount?.toString() ?: "0",
    emoji = emoji ?: "",
    accentColor = accentColor.hexToColorLong(),
    visitedAt = Instant.parse(visitedAt),
)

fun PlaceEntity.toDomain(): Place = Place(
    id = id,
    tripId = tripId,
    name = name,
    category = category,
    address = address,
    latitude = latitude,
    longitude = longitude,
    city = city,
    country = country,
    rating = rating,
    reviewCount = reviewCount,
    emoji = emoji,
    accentColor = accentColor,
    visitedAt = visitedAt,
)

fun PlaceDto.toDomain(): Place = Place(
    id = id,
    tripId = tripId,
    name = name,
    category = category ?: "",
    address = address ?: "",
    latitude = lat,
    longitude = lng,
    city = city ?: "",
    country = country ?: "",
    rating = rating?.toFloat() ?: 0f,
    reviewCount = reviewCount?.toString() ?: "0",
    emoji = emoji ?: "",
    accentColor = accentColor.hexToColorLong(),
    visitedAt = Instant.parse(visitedAt),
)
