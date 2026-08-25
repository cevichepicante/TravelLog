package com.travellog.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDate

@Entity(tableName = "schedules")
data class ScheduleEntity(
    @PrimaryKey val id: String,
    val title: String,
    val destinationsJson: String,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val color: Long,
    val createdAt: Instant,
)
