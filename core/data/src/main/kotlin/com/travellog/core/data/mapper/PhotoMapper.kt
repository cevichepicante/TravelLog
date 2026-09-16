package com.travellog.core.data.mapper

import com.travellog.core.model.MediaType
import com.travellog.core.model.Photo
import com.travellog.core.network.dto.photo.PhotoDto
import java.time.Instant

fun PhotoDto.toDomain(order: Int = 0): Photo = Photo(
    id = id,
    tripId = tripId,
    order = order,
    mediaType = MediaType.valueOf(mediaType),
    uri = url,
    thumbnailUrl = thumbnailUrl,
    latitude = lat,
    longitude = lng,
    city = city,
    country = country,
    placeName = placeName,
    memo = memo,
    emoji = emoji,
    accentColor = accentColor?.hexToColorLong(),
    takenAt = Instant.parse(takenAt),
    createdAt = Instant.parse(createdAt),
)
