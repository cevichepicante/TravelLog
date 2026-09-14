package com.travellog.core.data.repository

import com.travellog.core.model.Photo
import com.travellog.core.model.PresignedUpload
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun observePhotosByTrip(tripId: String): Flow<List<Photo>>
    suspend fun getPresignedUploads(fileName: String, contentType: String, count: Int = 1): List<PresignedUpload>
    suspend fun registerPhoto(
        tripId: String,
        photoKey: String,
        mediaType: String,
        takenAt: String,
        lat: Double? = null,
        lng: Double? = null,
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
