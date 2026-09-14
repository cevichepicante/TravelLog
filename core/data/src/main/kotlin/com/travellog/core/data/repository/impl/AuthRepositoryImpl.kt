package com.travellog.core.data.repository.impl

import com.travellog.core.data.TokenManager
import com.travellog.core.data.local.dao.UserDao
import com.travellog.core.data.mapper.toEntity
import com.travellog.core.data.mapper.toDomain
import com.travellog.core.data.repository.AuthRepository
import com.travellog.core.model.User
import com.travellog.core.network.api.AuthApi
import com.travellog.core.network.dto.auth.SignInRequest
import com.travellog.core.network.dto.auth.SignUpRequest
import com.travellog.core.network.dto.auth.TokenRefreshRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val userDao: UserDao,
    private val tokenManager: TokenManager,
) : AuthRepository {

    override fun isSignedIn(): Flow<Boolean> = tokenManager.isSignedIn()

    override suspend fun signUp(provider: String, idToken: String, name: String, handle: String): User {
        val response = authApi.signUp(SignUpRequest(provider, idToken, name, handle))
        val data = response.data
        tokenManager.saveTokens(data.accessToken, data.refreshToken)
        val user = requireNotNull(data.user) { "signUp response missing user" }
        userDao.upsert(user.toEntity())
        return user.toDomain()
    }

    override suspend fun signIn(provider: String, idToken: String): User {
        val response = authApi.signIn(SignInRequest(provider, idToken))
        val data = response.data
        tokenManager.saveTokens(data.accessToken, data.refreshToken)
        val user = requireNotNull(data.user) { "signIn response missing user" }
        userDao.upsert(user.toEntity())
        return user.toDomain()
    }

    override suspend fun refreshToken() {
        val refreshToken = requireNotNull(tokenManager.refreshToken()) { "No refresh token stored" }
        val response = authApi.refreshToken(TokenRefreshRequest(refreshToken))
        tokenManager.saveTokens(response.data.accessToken, response.data.refreshToken)
    }

    override suspend fun signOut() {
        runCatching { authApi.signOut() }
        tokenManager.clearTokens()
    }
}
