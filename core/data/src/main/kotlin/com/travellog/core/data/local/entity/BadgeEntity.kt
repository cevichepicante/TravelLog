package com.travellog.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.travellog.core.model.BadgeType
import java.time.Instant

@Entity(tableName = "badges")
data class BadgeEntity(
    @PrimaryKey val id: String,
    val type: BadgeType,
    val country: String,
    val city: String?,
    val emoji: String,
    val label: String,
    val isRare: Boolean,
    val designId: String,
    val earnedAt: Instant,
)
