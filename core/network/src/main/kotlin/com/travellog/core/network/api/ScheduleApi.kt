package com.travellog.core.network.api

import com.travellog.core.network.dto.common.ApiResponse
import com.travellog.core.network.dto.common.ItemsData
import com.travellog.core.network.dto.schedule.CreateScheduleRequest
import com.travellog.core.network.dto.schedule.DestinationSuggestionDto
import com.travellog.core.network.dto.schedule.ScheduleDto
import com.travellog.core.network.dto.schedule.UpdateScheduleRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleApi {

    @GET("schedules")
    suspend fun getSchedules(): ApiResponse<ItemsData<ScheduleDto>>

    @POST("schedules")
    suspend fun createSchedule(@Body request: CreateScheduleRequest): ApiResponse<ScheduleDto>

    @GET("schedules/{scheduleId}")
    suspend fun getSchedule(@Path("scheduleId") scheduleId: String): ApiResponse<ScheduleDto>

    @PATCH("schedules/{scheduleId}")
    suspend fun updateSchedule(
        @Path("scheduleId") scheduleId: String,
        @Body request: UpdateScheduleRequest,
    ): ApiResponse<ScheduleDto>

    @DELETE("schedules/{scheduleId}")
    suspend fun deleteSchedule(@Path("scheduleId") scheduleId: String)

    @GET("schedules/destinations/suggestions")
    suspend fun getDestinationSuggestions(
        @Query("city") city: String,
    ): ApiResponse<DestinationSuggestionDto>
}
