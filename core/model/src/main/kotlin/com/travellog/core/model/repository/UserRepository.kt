package com.travellog.core.model.repository

import com.travellog.core.model.Badge
import com.travellog.core.model.PublicUser
import com.travellog.core.model.User
import com.travellog.core.model.UserStats
import com.travellog.core.model.Visibility

interface UserRepository {
    suspend fun getMe(): User
    suspend fun updateMe(
        name: String? = null,
        avatarEmoji: String? = null,
        title: String? = null,
        visibility: Visibility? = null,
    ): User
    suspend fun getStats(): UserStats
    suspend fun getPublicUser(userId: String): PublicUser
    suspend fun getMyBadges(type: String? = null): List<Badge>
}
