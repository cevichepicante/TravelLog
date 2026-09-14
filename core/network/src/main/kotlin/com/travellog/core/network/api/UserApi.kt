package com.travellog.core.network.api

import com.travellog.core.network.dto.badge.BadgeDto
import com.travellog.core.network.dto.common.ApiResponse
import com.travellog.core.network.dto.common.ItemsData
import com.travellog.core.network.dto.user.PublicUserDto
import com.travellog.core.network.dto.user.UpdateUserRequest
import com.travellog.core.network.dto.user.UserDto
import com.travellog.core.network.dto.user.UserStatsDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("users/me")
    suspend fun getMe(): ApiResponse<UserDto>

    @PATCH("users/me")
    suspend fun updateMe(@Body request: UpdateUserRequest): ApiResponse<UserDto>

    @GET("users/me/stats")
    suspend fun getMyStats(): ApiResponse<UserStatsDto>

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): ApiResponse<PublicUserDto>

    @GET("users/me/badges")
    suspend fun getMyBadges(@Query("type") type: String? = null): ApiResponse<ItemsData<BadgeDto>>
}
