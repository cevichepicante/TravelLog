package com.travellog.core.model

import java.time.Instant

data class Place(
    val id: String,
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
