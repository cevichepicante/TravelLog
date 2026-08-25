package com.travellog.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.travellog.core.model.Visibility

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val handle: String,
    val avatarEmoji: String,
    val title: String,
    val visibility: Visibility,
    val storageUsedBytes: Long,
    val storageMaxBytes: Long,
    val isPro: Boolean,
)
