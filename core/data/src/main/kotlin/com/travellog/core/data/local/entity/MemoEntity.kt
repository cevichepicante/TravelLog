package com.travellog.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "memos",
    foreignKeys = [ForeignKey(
        entity = TripEntity::class,
        parentColumns = ["id"],
        childColumns = ["tripId"],
        onDelete = ForeignKey.CASCADE,
    )],
    indices = [Index("tripId")],
)
data class MemoEntity(
    @PrimaryKey val id: String,
    val tripId: String,
    val photoId: String?,
    val text: String,
    val city: String,
    val accentColor: Long,
    val createdAt: Instant,
)
