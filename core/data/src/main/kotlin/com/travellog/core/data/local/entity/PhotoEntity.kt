package com.travellog.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.travellog.core.model.MediaType
import java.time.Instant

@Entity(
    tableName = "photos",
    foreignKeys = [ForeignKey(
        entity = TripEntity::class,
        parentColumns = ["id"],
        childColumns = ["tripId"],
        onDelete = ForeignKey.CASCADE,
    )],
    indices = [Index("tripId")],
)
data class PhotoEntity(
    @PrimaryKey val id: String,
    val tripId: String,
    val type: MediaType,
    val uri: String,
    val latitude: Double?,
    val longitude: Double?,
    val city: String?,
    val placeName: String?,
    val memo: String?,
    val emoji: String,
    val accentColor: Long,
    val takenAt: Instant,
    val createdAt: Instant,
)
