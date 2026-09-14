package com.travellog.core.network.api

import com.travellog.core.network.dto.badge.BadgeDesignDto
import com.travellog.core.network.dto.badge.BadgeDto
import com.travellog.core.network.dto.badge.UpdateBadgeDesignRequest
import com.travellog.core.network.dto.common.ApiResponse
import com.travellog.core.network.dto.common.ItemsData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface BadgeApi {

    @GET("badges/{badgeId}")
    suspend fun getBadge(@Path("badgeId") badgeId: String): ApiResponse<BadgeDto>

    @GET("badges/designs")
    suspend fun getBadgeDesigns(): ApiResponse<ItemsData<BadgeDesignDto>>

    @PATCH("badges/{badgeId}/design")
    suspend fun updateBadgeDesign(
        @Path("badgeId") badgeId: String,
        @Body request: UpdateBadgeDesignRequest,
    ): ApiResponse<BadgeDto>
}
