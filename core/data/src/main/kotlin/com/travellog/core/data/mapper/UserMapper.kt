package com.travellog.core.data.mapper

import com.travellog.core.data.local.entity.UserEntity
import com.travellog.core.model.Friend
import com.travellog.core.model.FriendRequest
import com.travellog.core.model.MapPin
import com.travellog.core.model.FriendMapPin
import com.travellog.core.model.PublicUser
import com.travellog.core.model.User
import com.travellog.core.model.UserStats
import com.travellog.core.model.Visibility
import com.travellog.core.network.dto.friend.FriendDto
import com.travellog.core.network.dto.friend.FriendRequestDto
import com.travellog.core.network.dto.map.FriendPinDto
import com.travellog.core.network.dto.map.PinDto
import com.travellog.core.network.dto.user.PublicUserDto
import com.travellog.core.network.dto.user.UserDto
import com.travellog.core.network.dto.user.UserStatsDto
import java.time.Instant

fun UserDto.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name,
    handle = handle,
    avatarEmoji = avatarEmoji,
    title = title,
    visibility = Visibility.valueOf(visibility),
    storageUsedBytes = storageUsedBytes,
    storageMaxBytes = storageTotalBytes,
    isPro = isPro,
)

fun UserEntity.toDomain(): User = User(
    id = id,
    name = name,
    handle = handle,
    avatarEmoji = avatarEmoji,
    title = title,
    visibility = visibility,
    storageUsedBytes = storageUsedBytes,
    storageMaxBytes = storageMaxBytes,
    isPro = isPro,
)

fun UserDto.toDomain(): User = User(
    id = id,
    name = name,
    handle = handle,
    avatarEmoji = avatarEmoji,
    title = title,
    visibility = Visibility.valueOf(visibility),
    storageUsedBytes = storageUsedBytes,
    storageMaxBytes = storageTotalBytes,
    isPro = isPro,
)

fun UserStatsDto.toDomain(): UserStats = UserStats(
    countryCnt = countryCnt,
    stateCnt = stateCnt,
    photoCnt = photoCnt,
    placeCnt = placeCnt,
    tripCnt = tripCnt,
    totalSteps = totalSteps,
)

fun PublicUserDto.toDomain(): PublicUser = PublicUser(
    id = id,
    name = name,
    handle = handle,
    avatarEmoji = avatarEmoji,
    title = title,
    tripCnt = tripCnt,
    countryCnt = countryCnt,
)

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

fun PinDto.toDomain(): MapPin = MapPin(
    photoId = photoId,
    thumbnailUrl = thumbnailUrl,
    latitude = lat,
    longitude = lng,
    takenAt = Instant.parse(takenAt),
)

fun FriendPinDto.toDomain(): FriendMapPin = FriendMapPin(
    userId = userId,
    name = name,
    avatarEmoji = avatarEmoji,
    photoId = photoId,
    thumbnailUrl = thumbnailUrl,
    latitude = lat,
    longitude = lng,
    takenAt = Instant.parse(takenAt),
)
