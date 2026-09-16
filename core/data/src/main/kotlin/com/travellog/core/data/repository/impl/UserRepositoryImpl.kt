package com.travellog.core.data.repository.impl

import com.travellog.core.data.mapper.toDomain
import com.travellog.core.model.Badge
import com.travellog.core.model.PublicUser
import com.travellog.core.model.User
import com.travellog.core.model.UserStats
import com.travellog.core.model.Visibility
import com.travellog.core.model.repository.UserRepository
import com.travellog.core.network.api.UserApi
import com.travellog.core.network.dto.user.UpdateUserRequest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
) : UserRepository {

    override suspend fun getMe(): User =
        userApi.getMe().data.toDomain()

    override suspend fun updateMe(
        name: String?,
        avatarEmoji: String?,
        title: String?,
        visibility: Visibility?,
    ): User =
        userApi.updateMe(UpdateUserRequest(name, avatarEmoji, title, visibility?.name)).data.toDomain()

    override suspend fun getStats(): UserStats =
        userApi.getMyStats().data.toDomain()

    override suspend fun getPublicUser(userId: String): PublicUser =
        userApi.getUser(userId).data.toDomain()

    override suspend fun getMyBadges(type: String?): List<Badge> =
        userApi.getMyBadges(type).data.items.map { it.toDomain() }
}
