package com.travellog.core.network.dto.photo

import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    val id: String,
    val tripId: String,
    val mediaType: String,
    val url: String,
    val thumbnailUrl: String,
    val lat: Double? = null,
    val lng: Double? = null,
    val city: String? = null,
    val country: String? = null,
    val placeName: String? = null,
    val memo: String? = null,
    val emoji: String? = null,
    val accentColor: String? = null,
    val takenAt: String,
    val createdAt: String,
)

@Serializable
data class PresignedUrlRequest(
    val fileName: String,
    val contentType: String,
    val count: Int = 1,
)

@Serializable
data class PresignedUrlItem(
    val photoKey: String,
    val presignedUrl: String,
    val expiresAt: String,
)

@Serializable
data class PresignedUrlResponseData(val uploads: List<PresignedUrlItem>)

@Serializable
data class RegisterPhotoRequest(
    val tripId: String,
    val photoKey: String,
    val mediaType: String,
    val takenAt: String,
    val lat: Double? = null,
    val lng: Double? = null,
    val placeName: String? = null,
    val city: String? = null,
    val country: String? = null,
    val memo: String? = null,
    val emoji: String? = null,
)

@Serializable
data class RegisterPhotoResponseData(
    val photo: PhotoDto,
    val awardedBadgeIds: List<String>,
)

@Serializable
data class UpdatePhotoRequest(
    val tripId: String? = null,
    val placeName: String? = null,
    val memo: String? = null,
    val emoji: String? = null,
)
