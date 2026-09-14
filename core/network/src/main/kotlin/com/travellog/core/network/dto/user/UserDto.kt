package com.travellog.core.network.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val name: String,
    val handle: String,
    val avatarEmoji: String,
    val title: String,
    val visibility: String,
    val storageUsedBytes: Long,
    val storageTotalBytes: Long,
    val isPro: Boolean,
)

@Serializable
data class UpdateUserRequest(
    val name: String? = null,
    val avatarEmoji: String? = null,
    val title: String? = null,
    val visibility: String? = null,
)

@Serializable
data class UserStatsDto(
    val countryCnt: Int,
    val stateCnt: Int,
    val photoCnt: Int,
    val placeCnt: Int,
    val tripCnt: Int,
    val totalSteps: Long,
)

@Serializable
data class PublicUserDto(
    val id: String,
    val name: String,
    val handle: String,
    val avatarEmoji: String,
    val title: String,
    val tripCnt: Int,
    val countryCnt: Int,
)
