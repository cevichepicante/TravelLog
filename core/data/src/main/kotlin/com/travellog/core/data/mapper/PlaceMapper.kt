package com.travellog.core.data.mapper

import com.travellog.core.model.Place
import com.travellog.core.model.PlaceSearchResult
import com.travellog.core.network.dto.place.PlaceDto
import com.travellog.core.network.dto.place.PlaceSearchResultDto
import java.time.Instant

fun PlaceDto.toDomain(order: Int = 0): Place = Place(
    id = id,
    tripId = tripId,
    order = order,
    name = name,
    latitude = lat,
    longitude = lng,
    category = category,
    address = address,
    city = city,
    country = country,
    rating = rating,
    reviewCount = reviewCount,
    emoji = emoji,
    accentColor = accentColor?.hexToColorLong(),
    visitedAt = Instant.parse(visitedAt),
    createdAt = Instant.parse(createdAt),
)

fun PlaceSearchResultDto.toDomain(): PlaceSearchResult = PlaceSearchResult(
    name = name,
    category = category,
    address = address,
    latitude = lat,
    longitude = lng,
)
