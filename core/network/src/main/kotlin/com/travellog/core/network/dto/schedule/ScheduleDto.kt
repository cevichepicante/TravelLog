package com.travellog.core.network.dto.schedule

import kotlinx.serialization.Serializable

@Serializable
data class DestinationDto(
    val country: String,
    val city: String,
    val flagEmoji: String,
)

@Serializable
data class ScheduleDto(
    val id: String,
    val title: String,
    val destinations: List<DestinationDto>,
    val startDate: String,
    val endDate: String,
    val color: String? = null,
    val createdAt: String,
)

@Serializable
data class CreateScheduleRequest(
    val title: String,
    val destinations: List<DestinationDto>,
    val startDate: String,
    val endDate: String,
    val color: String? = null,
)

@Serializable
data class UpdateScheduleRequest(
    val title: String? = null,
    val destinations: List<DestinationDto>? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val color: String? = null,
)

@Serializable
data class PlaceSuggestionDto(
    val name: String,
    val category: String,
    val description: String,
    val emoji: String,
)

@Serializable
data class DestinationSuggestionDto(
    val city: String,
    val places: List<PlaceSuggestionDto>,
    val tips: List<String>,
)
