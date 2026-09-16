package com.travellog.core.data.mapper

import com.travellog.core.model.Destination
import com.travellog.core.model.DestinationSuggestion
import com.travellog.core.model.PlaceSuggestion
import com.travellog.core.model.Schedule
import com.travellog.core.network.dto.schedule.DestinationDto
import com.travellog.core.network.dto.schedule.DestinationSuggestionDto
import com.travellog.core.network.dto.schedule.ScheduleDto
import java.time.Instant
import java.time.LocalDate

fun Destination.toDto(): DestinationDto = DestinationDto(
    country = country,
    city = city,
    flagEmoji = flagEmoji,
)

fun ScheduleDto.toDomain(): Schedule = Schedule(
    id = id,
    title = title,
    destinations = destinations.map { Destination(it.country, it.city, it.flagEmoji) },
    startDate = LocalDate.parse(startDate),
    endDate = LocalDate.parse(endDate),
    accentColor = color?.hexToColorLong(),
    createdAt = Instant.parse(createdAt),
)

fun DestinationSuggestionDto.toDomain(): DestinationSuggestion = DestinationSuggestion(
    city = city,
    places = places.map { PlaceSuggestion(it.name, it.category, it.description, it.emoji) },
    tips = tips,
)
