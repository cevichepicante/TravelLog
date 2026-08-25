package com.travellog.core.model

import java.time.Instant

enum class BadgeType { COUNTRY, CITY }

data class Badge(
    val id: String,
    val type: BadgeType,
    val country: String,
    val city: String?,
    val emoji: String,
    val label: String,
    val isRare: Boolean,
    val designId: String,
    val earnedAt: Instant,
)
