package com.travellog.core.model

import java.time.Instant
import java.time.LocalDate

data class Schedule(
    val id: String,
    val title: String,
    val destinations: List<Destination>,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val color: Long,
    val createdAt: Instant,
)

data class Destination(
    val country: String,
    val city: String?,
    val flag: String,
)

data class DestinationSuggestion(
    val city: String,
    val places: List<PlaceSuggestion>,
    val tips: List<String>,
)

data class PlaceSuggestion(
    val name: String,
    val category: String,
    val description: String,
    val emoji: String,
)
