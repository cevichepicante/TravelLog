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
