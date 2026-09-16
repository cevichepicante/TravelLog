package com.travellog.core.model.repository

import com.travellog.core.model.Friend
import com.travellog.core.model.FriendRequest

interface FriendRepository {
    suspend fun getFriends(): List<Friend>
    suspend fun sendFriendRequest(targetUserId: String)
    suspend fun getFriendRequests(): List<FriendRequest>
    suspend fun acceptFriendRequest(requestId: String)
    suspend fun rejectFriendRequest(requestId: String)
    suspend fun deleteFriend(userId: String)
}
