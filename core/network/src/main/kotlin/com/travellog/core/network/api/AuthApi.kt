package com.travellog.core.network.api

import com.travellog.core.network.dto.auth.AuthResponseData
import com.travellog.core.network.dto.auth.SignInRequest
import com.travellog.core.network.dto.auth.SignUpRequest
import com.travellog.core.network.dto.auth.TokenRefreshRequest
import com.travellog.core.network.dto.common.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/sign-up")
    suspend fun signUp(@Body request: SignUpRequest): ApiResponse<AuthResponseData>

    @POST("auth/sign-in")
    suspend fun signIn(@Body request: SignInRequest): ApiResponse<AuthResponseData>

    @POST("auth/token/refresh")
    suspend fun refreshToken(@Body request: TokenRefreshRequest): ApiResponse<AuthResponseData>

    @POST("auth/sign-out")
    suspend fun signOut()
}
