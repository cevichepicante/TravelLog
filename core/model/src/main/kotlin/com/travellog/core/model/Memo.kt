package com.travellog.core.model

import java.time.Instant

data class Memo(
    override val id: String,
    override val tripId: String,
    override val order: Int,
    val text: String,
    val city: String? = null,
    val accentColor: Long? = null,
    val createdAt: Instant? = null,
) : RecordItem
