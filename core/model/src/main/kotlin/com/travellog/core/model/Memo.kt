package com.travellog.core.model

import java.time.Instant

data class Memo(
    val id: String,
    val tripId: String,
    val photoId: String?,
    val text: String,
    val city: String,
    val accentColor: Long,
    val createdAt: Instant,
)
