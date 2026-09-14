package com.travellog.core.data.repository

import com.travellog.core.model.Badge
import com.travellog.core.model.PublicUser
import com.travellog.core.model.User
import com.travellog.core.model.UserStats
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeCurrentUser(): Flow<User?>
    suspend fun refreshCurrentUser()
    suspend fun updateMe(
        name: String? = null,
        avatarEmoji: String? = null,
        title: String? = null,
        visibility: String? = null,
    ): User
    suspend fun getStats(): UserStats
    suspend fun getPublicUser(userId: String): PublicUser
    suspend fun getMyBadges(type: String? = null): List<Badge>
}
