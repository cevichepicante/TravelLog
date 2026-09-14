package com.travellog.core.model

enum class Visibility { PRIVATE, FRIENDS }

data class User(
    val id: String,
    val name: String,
    val handle: String,
    val avatarEmoji: String,
    val title: String,
    val visibility: Visibility,
    val storageUsedBytes: Long,
    val storageMaxBytes: Long,
    val isPro: Boolean,
)

data class UserStats(
    val countryCnt: Int,
    val stateCnt: Int,
    val photoCnt: Int,
    val placeCnt: Int,
    val tripCnt: Int,
    val totalSteps: Long,
)

data class PublicUser(
    val id: String,
    val name: String,
    val handle: String,
    val avatarEmoji: String,
    val title: String,
    val tripCnt: Int,
    val countryCnt: Int,
)
