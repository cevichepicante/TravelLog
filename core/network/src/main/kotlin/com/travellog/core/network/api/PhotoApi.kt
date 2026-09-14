package com.travellog.core.network.api

import com.travellog.core.network.dto.common.ApiResponse
import com.travellog.core.network.dto.photo.PhotoDto
import com.travellog.core.network.dto.photo.PresignedUrlRequest
import com.travellog.core.network.dto.photo.PresignedUrlResponseData
import com.travellog.core.network.dto.photo.RegisterPhotoRequest
import com.travellog.core.network.dto.photo.RegisterPhotoResponseData
import com.travellog.core.network.dto.photo.UpdatePhotoRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PhotoApi {

    @POST("photos/presigned-url")
    suspend fun getPresignedUrl(@Body request: PresignedUrlRequest): ApiResponse<PresignedUrlResponseData>

    @POST("photos")
    suspend fun registerPhoto(@Body request: RegisterPhotoRequest): ApiResponse<RegisterPhotoResponseData>

    @PATCH("photos/{photoId}")
    suspend fun updatePhoto(
        @Path("photoId") photoId: String,
        @Body request: UpdatePhotoRequest,
    ): ApiResponse<PhotoDto>

    @DELETE("photos/{photoId}")
    suspend fun deletePhoto(@Path("photoId") photoId: String)
}
