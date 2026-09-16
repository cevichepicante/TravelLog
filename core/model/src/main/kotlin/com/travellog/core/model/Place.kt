package com.travellog.core.model

import java.time.Instant

data class Place(
    override val id: String,
    override val tripId: String,
    override val order: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val category: String? = null,
    val address: String? = null,
    val city: String? = null,
    val country: String? = null,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val emoji: String? = null,
    val accentColor: Long? = null,
    val visitedAt: Instant? = null,
    val createdAt: Instant? = null,
) : RecordItem
