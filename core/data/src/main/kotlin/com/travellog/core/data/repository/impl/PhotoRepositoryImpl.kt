package com.travellog.core.data.repository.impl

import com.travellog.core.data.mapper.toDomain
import com.travellog.core.model.MediaType
import com.travellog.core.model.Photo
import com.travellog.core.model.PresignedUpload
import com.travellog.core.model.repository.PhotoRepository
import com.travellog.core.network.api.PhotoApi
import com.travellog.core.network.dto.photo.PresignedUrlRequest
import com.travellog.core.network.dto.photo.RegisterPhotoRequest
import com.travellog.core.network.dto.photo.UpdatePhotoRequest
import java.time.Instant
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoApi: PhotoApi,
) : PhotoRepository {

    override suspend fun getPresignedUploads(fileName: String, contentType: String, count: Int): List<PresignedUpload> =
        photoApi.getPresignedUrl(PresignedUrlRequest(fileName, contentType, count))
            .data.uploads
            .map { PresignedUpload(it.photoKey, it.presignedUrl, Instant.parse(it.expiresAt)) }

    override suspend fun registerPhoto(
        tripId: String,
        photoKey: String,
        mediaType: MediaType,
        takenAt: Instant,
        latitude: Double?,
        longitude: Double?,
        placeName: String?,
        city: String?,
        country: String?,
        memo: String?,
        emoji: String?,
    ): Pair<Photo, List<String>> {
        val response = photoApi.registerPhoto(
            RegisterPhotoRequest(
                tripId, photoKey, mediaType.name, takenAt.toString(),
                latitude, longitude, placeName, city, country, memo, emoji,
            )
        ).data
        return Pair(response.photo.toDomain(), response.awardedBadgeIds)
    }

    override suspend fun updatePhoto(
        photoId: String,
        tripId: String?,
        placeName: String?,
        memo: String?,
        emoji: String?,
    ): Photo =
        photoApi.updatePhoto(photoId, UpdatePhotoRequest(tripId, placeName, memo, emoji)).data.toDomain()

    override suspend fun deletePhoto(photoId: String) {
        photoApi.deletePhoto(photoId)
    }
}
