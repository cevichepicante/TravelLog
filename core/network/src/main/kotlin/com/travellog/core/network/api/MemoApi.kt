package com.travellog.core.network.api

import com.travellog.core.network.dto.common.ApiResponse
import com.travellog.core.network.dto.memo.CreateMemoRequest
import com.travellog.core.network.dto.memo.MemoDto
import com.travellog.core.network.dto.memo.UpdateMemoRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface MemoApi {

    @POST("memos")
    suspend fun createMemo(@Body request: CreateMemoRequest): ApiResponse<MemoDto>

    @PATCH("memos/{memoId}")
    suspend fun updateMemo(
        @Path("memoId") memoId: String,
        @Body request: UpdateMemoRequest,
    ): ApiResponse<MemoDto>

    @DELETE("memos/{memoId}")
    suspend fun deleteMemo(@Path("memoId") memoId: String)
}
