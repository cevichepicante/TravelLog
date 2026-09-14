package com.travellog.core.network.api

import com.travellog.core.network.dto.common.ApiResponse
import com.travellog.core.network.dto.common.ItemsData
import com.travellog.core.network.dto.place.CreatePlaceRequest
import com.travellog.core.network.dto.place.CreatePlaceResponseData
import com.travellog.core.network.dto.place.PlaceDto
import com.travellog.core.network.dto.place.PlaceSearchResultDto
import com.travellog.core.network.dto.place.UpdatePlaceRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceApi {

    @GET("places/search")
    suspend fun searchPlaces(
        @Query("q") query: String,
        @Query("lat") lat: Double? = null,
        @Query("lng") lng: Double? = null,
    ): ApiResponse<ItemsData<PlaceSearchResultDto>>

    @POST("places")
    suspend fun createPlace(@Body request: CreatePlaceRequest): ApiResponse<CreatePlaceResponseData>

    @PATCH("places/{placeId}")
    suspend fun updatePlace(
        @Path("placeId") placeId: String,
        @Body request: UpdatePlaceRequest,
    ): ApiResponse<PlaceDto>

    @DELETE("places/{placeId}")
    suspend fun deletePlace(@Path("placeId") placeId: String)
}
