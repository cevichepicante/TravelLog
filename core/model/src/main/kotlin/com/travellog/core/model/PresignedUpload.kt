package com.travellog.core.model

import java.time.Instant

data class PresignedUpload(
    val photoKey: String,
    val presignedUrl: String,
    val expiresAt: Instant,
)
