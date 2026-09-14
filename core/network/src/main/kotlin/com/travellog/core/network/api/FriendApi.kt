package com.travellog.core.network.api

import com.travellog.core.network.dto.common.ApiResponse
import com.travellog.core.network.dto.common.ItemsData
import com.travellog.core.network.dto.friend.FriendDto
import com.travellog.core.network.dto.friend.FriendRequestDto
import com.travellog.core.network.dto.friend.RespondFriendRequestBody
import com.travellog.core.network.dto.friend.RespondFriendRequestResponseData
import com.travellog.core.network.dto.friend.SendFriendRequestBody
import com.travellog.core.network.dto.friend.SendFriendRequestResponseData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface FriendApi {

    @GET("friends")
    suspend fun getFriends(): ApiResponse<ItemsData<FriendDto>>

    @POST("friends/requests")
    suspend fun sendFriendRequest(
        @Body request: SendFriendRequestBody,
    ): ApiResponse<SendFriendRequestResponseData>

    @GET("friends/requests")
    suspend fun getFriendRequests(): ApiResponse<ItemsData<FriendRequestDto>>

    @PATCH("friends/requests/{requestId}")
    suspend fun respondFriendRequest(
        @Path("requestId") requestId: String,
        @Body request: RespondFriendRequestBody,
    ): ApiResponse<RespondFriendRequestResponseData>

    @DELETE("friends/{userId}")
    suspend fun deleteFriend(@Path("userId") userId: String)
}
