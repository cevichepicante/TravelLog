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
    val storageTotalBytes: Long,
    val isPro: Boolean,
)

data class UserStats(
    val countryCount: Int,
    val stateCount: Int,
    val photoCount: Int,
    val placeCount: Int,
    val tripCount: Int,
    val totalSteps: Long,
)

data class PublicUser(
    val id: String,
    val name: String,
    val handle: String,
    val avatarEmoji: String,
    val title: String,
    val tripCount: Int,
    val countryCount: Int,
)
