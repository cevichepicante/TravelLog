package com.travellog.core.network.dto.map

import kotlinx.serialization.Serializable

@Serializable
data class PinDto(
    val photoId: String,
    val thumbnailUrl: String,
    val lat: Double,
    val lng: Double,
    val takenAt: String,
)

@Serializable
data class FriendPinDto(
    val userId: String,
    val name: String,
    val avatarEmoji: String,
    val photoId: String,
    val thumbnailUrl: String,
    val lat: Double,
    val lng: Double,
    val takenAt: String,
)
