package com.travellog.core.data.mapper

import com.travellog.core.model.PublicUser
import com.travellog.core.model.User
import com.travellog.core.model.UserStats
import com.travellog.core.model.Visibility
import com.travellog.core.network.dto.user.PublicUserDto
import com.travellog.core.network.dto.user.UserDto
import com.travellog.core.network.dto.user.UserStatsDto

fun UserDto.toDomain(): User = User(
    id = id,
    name = name,
    handle = handle,
    avatarEmoji = avatarEmoji,
    title = title,
    visibility = Visibility.valueOf(visibility),
    storageUsedBytes = storageUsedBytes,
    storageTotalBytes = storageTotalBytes,
    isPro = isPro,
)

fun UserStatsDto.toDomain(): UserStats = UserStats(
    countryCount = countryCnt,
    stateCount = stateCnt,
    photoCount = photoCnt,
    placeCount = placeCnt,
    tripCount = tripCnt,
    totalSteps = totalSteps,
)

fun PublicUserDto.toDomain(): PublicUser = PublicUser(
    id = id,
    name = name,
    handle = handle,
    avatarEmoji = avatarEmoji,
    title = title,
    tripCount = tripCnt,
    countryCount = countryCnt,
)
