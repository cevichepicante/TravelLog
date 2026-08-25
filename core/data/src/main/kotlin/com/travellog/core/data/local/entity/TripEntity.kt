package com.travellog.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDate

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey val id: String,
    val title: String,
    val flag: String,
    val country: String,
    val cities: List<String>,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val photoCount: Int,
    val placeCount: Int,
    val badgeCount: Int,
    val accentColor: Long,
    val createdAt: Instant,
    val updatedAt: Instant,
)
