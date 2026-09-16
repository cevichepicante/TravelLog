package com.travellog.core.model.repository

import com.travellog.core.model.MediaType
import com.travellog.core.model.Photo
import com.travellog.core.model.PresignedUpload
import java.time.Instant

interface PhotoRepository {
    suspend fun getPresignedUploads(fileName: String, contentType: String, count: Int = 1): List<PresignedUpload>
    suspend fun registerPhoto(
        tripId: String,
        photoKey: String,
        mediaType: MediaType,
        takenAt: Instant,
        latitude: Double? = null,
        longitude: Double? = null,
        placeName: String? = null,
        city: String? = null,
        country: String? = null,
        memo: String? = null,
        emoji: String? = null,
    ): Pair<Photo, List<String>>
    suspend fun updatePhoto(
        photoId: String,
        tripId: String? = null,
        placeName: String? = null,
        memo: String? = null,
        emoji: String? = null,
    ): Photo
    suspend fun deletePhoto(photoId: String)
}
