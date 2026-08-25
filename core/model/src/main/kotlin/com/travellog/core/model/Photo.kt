package com.travellog.core.model

import java.time.Instant

enum class MediaType { PHOTO, VIDEO }

data class Photo(
    val id: String,
    val tripId: String,
    val type: MediaType,
    val uri: String,
    val latitude: Double?,
    val longitude: Double?,
    val city: String?,
    val placeName: String?,
    val memo: String?,
    val emoji: String,
    val accentColor: Long,
    val takenAt: Instant,
    val createdAt: Instant,
)
