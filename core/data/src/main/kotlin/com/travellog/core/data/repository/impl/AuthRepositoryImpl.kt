package com.travellog.core.data.repository.impl

import com.travellog.core.data.TokenManager
import com.travellog.core.data.mapper.toDomain
import com.travellog.core.model.User
import com.travellog.core.model.repository.AuthRepository
import com.travellog.core.network.api.AuthApi
import com.travellog.core.network.dto.auth.SignInRequest
import com.travellog.core.network.dto.auth.SignUpRequest
import com.travellog.core.network.dto.auth.TokenRefreshRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager,
) : AuthRepository {

    override fun isSignedIn(): Flow<Boolean> = tokenManager.isSignedIn()

    override suspend fun signUp(provider: String, idToken: String, name: String, handle: String): User {
        val data = authApi.signUp(SignUpRequest(provider, idToken, name, handle)).data
        tokenManager.saveTokens(data.accessToken, data.refreshToken)
        return requireNotNull(data.user) { "signUp response missing user" }.toDomain()
    }

    override suspend fun signIn(provider: String, idToken: String): User {
        val data = authApi.signIn(SignInRequest(provider, idToken)).data
        tokenManager.saveTokens(data.accessToken, data.refreshToken)
        return requireNotNull(data.user) { "signIn response missing user" }.toDomain()
    }

    override suspend fun refreshToken() {
        val refreshToken = requireNotNull(tokenManager.refreshToken()) { "No refresh token stored" }
        val data = authApi.refreshToken(TokenRefreshRequest(refreshToken)).data
        tokenManager.saveTokens(data.accessToken, data.refreshToken)
    }

    override suspend fun signOut() {
        runCatching { authApi.signOut() }
        tokenManager.clearTokens()
    }
}
