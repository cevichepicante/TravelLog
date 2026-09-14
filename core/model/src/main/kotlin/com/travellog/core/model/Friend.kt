package com.travellog.core.model

import java.time.Instant

data class Friend(
    val userId: String,
    val name: String,
    val handle: String,
    val avatarEmoji: String,
)

data class FriendRequest(
    val requestId: String,
    val fromUser: Friend,
    val requestedAt: Instant,
)
