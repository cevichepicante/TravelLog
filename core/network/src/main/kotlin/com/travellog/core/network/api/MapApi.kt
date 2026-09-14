package com.travellog.core.network.api

import com.travellog.core.network.dto.common.ApiResponse
import com.travellog.core.network.dto.common.ItemsData
import com.travellog.core.network.dto.map.FriendPinDto
import com.travellog.core.network.dto.map.PinDto
import retrofit2.http.GET

interface MapApi {

    @GET("map/pins")
    suspend fun getMyPins(): ApiResponse<ItemsData<PinDto>>

    @GET("map/friends/pins")
    suspend fun getFriendPins(): ApiResponse<ItemsData<FriendPinDto>>
}
