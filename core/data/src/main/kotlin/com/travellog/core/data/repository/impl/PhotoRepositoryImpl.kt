package com.travellog.core.data.repository.impl

import com.travellog.core.data.local.dao.PhotoDao
import com.travellog.core.data.mapper.toDomain
import com.travellog.core.data.mapper.toEntity
import com.travellog.core.data.repository.PhotoRepository
import com.travellog.core.model.Photo
import com.travellog.core.model.PresignedUpload
import com.travellog.core.network.api.PhotoApi
import com.travellog.core.network.dto.photo.PresignedUrlRequest
import com.travellog.core.network.dto.photo.RegisterPhotoRequest
import com.travellog.core.network.dto.photo.UpdatePhotoRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoApi: PhotoApi,
    private val photoDao: PhotoDao,
) : PhotoRepository {

    override fun observePhotosByTrip(tripId: String): Flow<List<Photo>> =
        photoDao.getByTrip(tripId).map { list -> list.map { it.toDomain() } }

    override suspend fun getPresignedUploads(
        fileName: String,
        contentType: String,
        count: Int,
    ): List<PresignedUpload> =
        photoApi.getPresignedUrl(PresignedUrlRequest(fileName, contentType, count))
            .data.uploads
            .map { PresignedUpload(it.photoKey, it.presignedUrl, Instant.parse(it.expiresAt)) }

    override suspend fun registerPhoto(
        tripId: String,
        photoKey: String,
        mediaType: String,
        takenAt: String,
        lat: Double?,
        lng: Double?,
        placeName: String?,
        city: String?,
        country: String?,
        memo: String?,
        emoji: String?,
    ): Pair<Photo, List<String>> {
        val response = photoApi.registerPhoto(
            RegisterPhotoRequest(tripId, photoKey, mediaType, takenAt, lat, lng, placeName, city, country, memo, emoji)
        ).data
        val photo = response.photo
        photoDao.upsert(photo.toEntity())
        return Pair(photo.toDomain(), response.awardedBadgeIds)
    }

    override suspend fun updatePhoto(
        photoId: String,
        tripId: String?,
        placeName: String?,
        memo: String?,
        emoji: String?,
    ): Photo {
        val photo = photoApi.updatePhoto(photoId, UpdatePhotoRequest(tripId, placeName, memo, emoji)).data
        photoDao.upsert(photo.toEntity())
        return photo.toDomain()
    }

    override suspend fun deletePhoto(photoId: String) {
        photoApi.deletePhoto(photoId)
        photoDao.deleteById(photoId)
    }
}
