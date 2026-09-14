package com.travellog.core.network.dto.common

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(val data: T)

@Serializable
data class ItemsData<T>(val items: List<T>)

@Serializable
data class PagedData<T>(
    val items: List<T>,
    val nextCursor: String? = null,
    val hasMore: Boolean = false,
)
