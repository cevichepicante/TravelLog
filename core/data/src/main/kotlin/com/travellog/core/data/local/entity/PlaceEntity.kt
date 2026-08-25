package com.travellog.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "places",
    foreignKeys = [ForeignKey(
        entity = TripEntity::class,
        parentColumns = ["id"],
        childColumns = ["tripId"],
        onDelete = ForeignKey.CASCADE,
    )],
    indices = [Index("tripId")],
)
data class PlaceEntity(
    @PrimaryKey val id: String,
    val tripId: String,
    val name: String,
    val category: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val country: String,
    val rating: Float,
    val reviewCount: String,
    val emoji: String,
    val accentColor: Long,
    val visitedAt: Instant,
)
