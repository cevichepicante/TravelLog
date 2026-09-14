package com.travellog.core.data.mapper

import com.travellog.core.data.local.entity.ScheduleEntity
import com.travellog.core.model.Destination
import com.travellog.core.model.DestinationSuggestion
import com.travellog.core.model.PlaceSuggestion
import com.travellog.core.model.Schedule
import com.travellog.core.network.dto.schedule.DestinationSuggestionDto
import com.travellog.core.network.dto.schedule.ScheduleDto
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDate

@Serializable
private data class DestinationJson(
    val country: String,
    val city: String?,
    val flag: String,
)

private val json = Json { ignoreUnknownKeys = true }

fun ScheduleDto.toEntity(): ScheduleEntity = ScheduleEntity(
    id = id,
    title = title,
    destinationsJson = json.encodeToString(
        destinations.map { DestinationJson(it.country, it.city, it.flagEmoji) }
    ),
    startDate = startDate.let { LocalDate.parse(it) },
    endDate = endDate.let { LocalDate.parse(it) },
    color = color.hexToColorLong(),
    createdAt = Instant.parse(createdAt),
)

fun ScheduleEntity.toDomain(): Schedule = Schedule(
    id = id,
    title = title,
    destinations = json.decodeFromString<List<DestinationJson>>(destinationsJson)
        .map { Destination(country = it.country, city = it.city, flag = it.flag) },
    startDate = startDate,
    endDate = endDate,
    color = color,
    createdAt = createdAt,
)

fun ScheduleDto.toDomain(): Schedule = Schedule(
    id = id,
    title = title,
    destinations = destinations.map { Destination(it.country, it.city, it.flagEmoji) },
    startDate = LocalDate.parse(startDate),
    endDate = LocalDate.parse(endDate),
    color = color.hexToColorLong(),
    createdAt = Instant.parse(createdAt),
)

fun DestinationSuggestionDto.toDomain(): DestinationSuggestion = DestinationSuggestion(
    city = city,
    places = places.map { PlaceSuggestion(it.name, it.category, it.description, it.emoji) },
    tips = tips,
)
