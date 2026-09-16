package com.travellog.core.data.mapper

import com.travellog.core.model.FriendMapPin
import com.travellog.core.model.MapPin
import com.travellog.core.network.dto.map.FriendPinDto
import com.travellog.core.network.dto.map.PinDto
import java.time.Instant

fun PinDto.toDomain(): MapPin = MapPin(
    photoId = photoId,
    thumbnailUrl = thumbnailUrl,
    latitude = lat,
    longitude = lng,
    takenAt = Instant.parse(takenAt),
)

fun FriendPinDto.toDomain(): FriendMapPin = FriendMapPin(
    userId = userId,
    name = name,
    avatarEmoji = avatarEmoji,
    photoId = photoId,
    thumbnailUrl = thumbnailUrl,
    latitude = lat,
    longitude = lng,
    takenAt = Instant.parse(takenAt),
)
