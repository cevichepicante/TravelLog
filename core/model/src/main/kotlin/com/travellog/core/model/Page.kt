package com.travellog.core.model

data class Page<T>(
    val items: List<T>,
    val nextCursor: String?,
    val hasMore: Boolean,
)
