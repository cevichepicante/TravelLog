package com.travellog.core.network.dto.memo

import kotlinx.serialization.Serializable

@Serializable
data class MemoDto(
    val id: String,
    val tripId: String,
    val text: String,
    val city: String? = null,
    val accentColor: String? = null,
    val createdAt: String,
)

@Serializable
data class CreateMemoRequest(
    val tripId: String,
    val text: String,
    val city: String? = null,
    val accentColor: String? = null,
)

@Serializable
data class UpdateMemoRequest(
    val text: String? = null,
    val accentColor: String? = null,
)
