package com.travellog.core.network.dto.auth

import com.travellog.core.network.dto.user.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val provider: String,
    val idToken: String,
    val name: String,
    val handle: String,
)

@Serializable
data class SignInRequest(
    val provider: String,
    val idToken: String,
)

@Serializable
data class TokenRefreshRequest(val refreshToken: String)

@Serializable
data class AuthResponseData(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDto? = null,
)
