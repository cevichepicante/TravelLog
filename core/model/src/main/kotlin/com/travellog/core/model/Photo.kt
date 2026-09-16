package com.travellog.core.model

import java.time.Instant

enum class MediaType { PHOTO, VIDEO }

data class Photo(
    override val id: String,
    override val tripId: String,
    override val order: Int,
    val mediaType: MediaType,
    val uri: String,
    val thumbnailUrl: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val city: String? = null,
    val country: String? = null,
    val placeName: String? = null,
    val memo: String? = null,
    val emoji: String? = null,
    val accentColor: Long? = null,
    val takenAt: Instant? = null,
    val createdAt: Instant? = null,
) : RecordItem
