package com.travellog.core.data.repository.impl

import com.travellog.core.data.local.dao.UserDao
import com.travellog.core.data.mapper.toDomain
import com.travellog.core.data.mapper.toEntity
import com.travellog.core.data.repository.UserRepository
import com.travellog.core.model.Badge
import com.travellog.core.model.PublicUser
import com.travellog.core.model.User
import com.travellog.core.model.UserStats
import com.travellog.core.network.api.UserApi
import com.travellog.core.network.dto.user.UpdateUserRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao,
) : UserRepository {

    override fun observeCurrentUser(): Flow<User?> =
        userDao.getCurrentUser().map { it?.toDomain() }

    override suspend fun refreshCurrentUser() {
        val user = userApi.getMe().data
        userDao.upsert(user.toEntity())
    }

    override suspend fun updateMe(
        name: String?,
        avatarEmoji: String?,
        title: String?,
        visibility: String?,
    ): User {
        val updated = userApi.updateMe(UpdateUserRequest(name, avatarEmoji, title, visibility)).data
        userDao.upsert(updated.toEntity())
        return updated.toDomain()
    }

    override suspend fun getStats(): UserStats =
        userApi.getMyStats().data.toDomain()

    override suspend fun getPublicUser(userId: String): PublicUser =
        userApi.getUser(userId).data.toDomain()

    override suspend fun getMyBadges(type: String?): List<Badge> =
        userApi.getMyBadges(type).data.items.map { it.toDomain() }
}
