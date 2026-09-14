package com.travellog.core.model

import java.time.Instant

sealed class BadgeType {
    data class Country(
        val id: String,
        val name: String,
    ) : BadgeType()

    data class State(
        val countryId: String,
        val name: String,
    ) : BadgeType()
}

data class Badge(
    val id: String,
    val type: BadgeType,
    val emoji: String,
    val label: String,
    val isRare: Boolean,
    val designId: String,
    val earnedAt: Instant,
)

data class BadgeDesign(
    val id: String,
    val name: String,
    val previewEmoji: String,
    val priceKrw: Int,
    val isPurchased: Boolean,
)
