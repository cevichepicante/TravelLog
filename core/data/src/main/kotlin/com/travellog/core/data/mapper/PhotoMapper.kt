package com.travellog.core.data.mapper

import com.travellog.core.data.local.entity.PhotoEntity
import com.travellog.core.model.MediaType
import com.travellog.core.model.Photo
import com.travellog.core.network.dto.photo.PhotoDto
import java.time.Instant

fun PhotoDto.toEntity(): PhotoEntity = PhotoEntity(
    id = id,
    tripId = tripId,
    type = MediaType.valueOf(mediaType),
    uri = url,
    latitude = lat,
    longitude = lng,
    city = city,
    placeName = placeName,
    memo = memo,
    emoji = emoji ?: "",
    accentColor = accentColor.hexToColorLong(),
    takenAt = Instant.parse(takenAt),
    createdAt = Instant.parse(createdAt),
)

fun PhotoEntity.toDomain(): Photo = Photo(
    id = id,
    tripId = tripId,
    type = type,
    uri = uri,
    latitude = latitude,
    longitude = longitude,
    city = city,
    placeName = placeName,
    memo = memo,
    emoji = emoji,
    accentColor = accentColor,
    takenAt = takenAt,
    createdAt = createdAt,
)

fun PhotoDto.toDomain(): Photo = Photo(
    id = id,
    tripId = tripId,
    type = MediaType.valueOf(mediaType),
    uri = url,
    latitude = lat,
    longitude = lng,
    city = city,
    placeName = placeName,
    memo = memo,
    emoji = emoji ?: "",
    accentColor = accentColor.hexToColorLong(),
    takenAt = Instant.parse(takenAt),
    createdAt = Instant.parse(createdAt),
)
