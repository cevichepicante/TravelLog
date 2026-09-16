package com.travellog.core.data.mapper

import com.travellog.core.model.Friend
import com.travellog.core.model.FriendRequest
import com.travellog.core.network.dto.friend.FriendDto
import com.travellog.core.network.dto.friend.FriendRequestDto
import java.time.Instant

fun FriendDto.toDomain(): Friend = Friend(
    userId = userId,
    name = name,
    handle = handle,
    avatarEmoji = avatarEmoji,
)

fun FriendRequestDto.toDomain(): FriendRequest = FriendRequest(
    requestId = requestId,
    fromUser = Friend(
        userId = fromUser.userId,
        name = fromUser.name,
        handle = fromUser.handle,
        avatarEmoji = fromUser.avatarEmoji,
    ),
    requestedAt = Instant.parse(requestedAt),
)
