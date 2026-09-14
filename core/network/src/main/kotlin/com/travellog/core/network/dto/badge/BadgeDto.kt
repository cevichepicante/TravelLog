package com.travellog.core.network.dto.badge

import kotlinx.serialization.Serializable

@Serializable
data class BadgeDto(
    val id: String,
    val type: String,
    val countryId: String? = null,
    val name: String,
    val emoji: String,
    val label: String,
    val isRare: Boolean,
    val designId: String,
    val earnedAt: String,
)

@Serializable
data class BadgeDesignDto(
    val id: String,
    val name: String,
    val previewEmoji: String,
    val priceKrw: Int,
    val isPurchased: Boolean,
)

@Serializable
data class UpdateBadgeDesignRequest(val designId: String)
