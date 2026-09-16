package com.travellog.core.data.repository.impl

import com.travellog.core.data.mapper.toDomain
import com.travellog.core.model.Friend
import com.travellog.core.model.FriendRequest
import com.travellog.core.model.repository.FriendRepository
import com.travellog.core.network.api.FriendApi
import com.travellog.core.network.dto.friend.RespondFriendRequestBody
import com.travellog.core.network.dto.friend.SendFriendRequestBody
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendApi: FriendApi,
) : FriendRepository {

    override suspend fun getFriends(): List<Friend> =
        friendApi.getFriends().data.items.map { it.toDomain() }

    override suspend fun sendFriendRequest(targetUserId: String) {
        friendApi.sendFriendRequest(SendFriendRequestBody(targetUserId))
    }

    override suspend fun getFriendRequests(): List<FriendRequest> =
        friendApi.getFriendRequests().data.items.map { it.toDomain() }

    override suspend fun acceptFriendRequest(requestId: String) {
        friendApi.respondFriendRequest(requestId, RespondFriendRequestBody("ACCEPT"))
    }

    override suspend fun rejectFriendRequest(requestId: String) {
        friendApi.respondFriendRequest(requestId, RespondFriendRequestBody("REJECT"))
    }

    override suspend fun deleteFriend(userId: String) {
        friendApi.deleteFriend(userId)
    }
}
