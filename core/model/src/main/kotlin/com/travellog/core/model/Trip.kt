package com.travellog.core.model

import java.time.Instant
import java.time.LocalDate

data class Trip(
    val id: String,
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
    val coverTiles: List<CoverTile>,
    val createdAt: Instant,
    val updatedAt: Instant,
)

data class CoverTile(val uri: String)
