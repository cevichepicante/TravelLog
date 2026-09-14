package com.travellog.core.network.api

import com.travellog.core.network.dto.common.ApiResponse
import com.travellog.core.network.dto.common.PagedData
import com.travellog.core.network.dto.trip.CreateTripRequest
import com.travellog.core.network.dto.trip.TimelineItemDto
import com.travellog.core.network.dto.trip.TripDto
import com.travellog.core.network.dto.trip.UpdateTripRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TripApi {

    @GET("trips")
    suspend fun getTrips(
        @Query("q") query: String? = null,
        @Query("cursor") cursor: String? = null,
        @Query("limit") limit: Int? = null,
    ): ApiResponse<PagedData<TripDto>>

    @POST("trips")
    suspend fun createTrip(@Body request: CreateTripRequest): ApiResponse<TripDto>

    @GET("trips/{tripId}")
    suspend fun getTrip(@Path("tripId") tripId: String): ApiResponse<TripDto>

    @PATCH("trips/{tripId}")
    suspend fun updateTrip(
        @Path("tripId") tripId: String,
        @Body request: UpdateTripRequest,
    ): ApiResponse<TripDto>

    @DELETE("trips/{tripId}")
    suspend fun deleteTrip(@Path("tripId") tripId: String)

    @GET("trips/{tripId}/timeline")
    suspend fun getTimeline(
        @Path("tripId") tripId: String,
        @Query("cursor") cursor: String? = null,
        @Query("limit") limit: Int? = null,
    ): ApiResponse<PagedData<TimelineItemDto>>
}
