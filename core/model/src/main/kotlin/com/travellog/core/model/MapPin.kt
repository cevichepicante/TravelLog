package com.travellog.core.model

import java.time.Instant

data class MapPin(
    val photoId: String,
    val thumbnailUrl: String,
    val latitude: Double,
    val longitude: Double,
    val takenAt: Instant,
)

data class FriendMapPin(
    val userId: String,
    val name: String,
    val avatarEmoji: String,
    val photoId: String,
    val thumbnailUrl: String,
    val latitude: Double,
    val longitude: Double,
    val takenAt: Instant,
)
