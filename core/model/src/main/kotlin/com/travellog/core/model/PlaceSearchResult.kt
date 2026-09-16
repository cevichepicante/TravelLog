package com.travellog.core.model

data class PlaceSearchResult(
    val name: String,
    val category: String?,
    val address: String?,
    val latitude: Double,
    val longitude: Double,
)
