package com.travellog.core.model

sealed interface RecordItem {
    val id: String
    val tripId: String
    val order: Int
}
