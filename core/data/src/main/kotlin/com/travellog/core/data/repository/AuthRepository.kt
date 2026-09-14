package com.travellog.core.data.repository

import com.travellog.core.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isSignedIn(): Flow<Boolean>
    suspend fun signUp(provider: String, idToken: String, name: String, handle: String): User
    suspend fun signIn(provider: String, idToken: String): User
    suspend fun refreshToken()
    suspend fun signOut()
}
