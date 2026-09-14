package com.travellog.core.network.dto.friend

import kotlinx.serialization.Serializable

@Serializable
data class FriendDto(
    val userId: String,
    val name: String,
    val handle: String,
    val avatarEmoji: String,
)

@Serializable
data class FriendRequestSenderDto(
    val userId: String,
    val name: String,
    val handle: String,
    val avatarEmoji: String,
)

@Serializable
data class FriendRequestDto(
    val requestId: String,
    val fromUser: FriendRequestSenderDto,
    val requestedAt: String,
)

@Serializable
data class SendFriendRequestBody(val targetUserId: String)

@Serializable
data class SendFriendRequestResponseData(
    val requestId: String,
    val status: String,
)

@Serializable
data class RespondFriendRequestBody(val action: String)

@Serializable
data class RespondFriendRequestResponseData(
    val requestId: String,
    val status: String,
)
