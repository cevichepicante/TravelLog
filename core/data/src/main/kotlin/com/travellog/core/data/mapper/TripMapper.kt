package com.travellog.core.data.mapper

import com.travellog.core.data.local.entity.TripEntity
import com.travellog.core.model.CoverTile
import com.travellog.core.model.Trip
import com.travellog.core.network.dto.trip.TripDto
import java.time.Instant
import java.time.LocalDate

fun TripDto.toEntity(): TripEntity = TripEntity(
    id = id,
    title = title,
    flag = flag,
    country = country,
    cities = cities,
    startDate = LocalDate.parse(startDate),
    endDate = LocalDate.parse(endDate),
    photoCount = photoCnt,
    placeCount = placeCnt,
    badgeCount = badgeCnt,
    accentColor = accentColor.hexToColorLong(),
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt),
)

fun TripEntity.toDomain(): Trip = Trip(
    id = id,
    title = title,
    flag = flag,
    country = country,
    cities = cities,
    startDate = startDate,
    endDate = endDate,
    photoCount = photoCount,
    placeCount = placeCount,
    badgeCount = badgeCount,
    accentColor = accentColor,
    coverTiles = emptyList(),
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun TripDto.toDomain(): Trip = Trip(
    id = id,
    title = title,
    flag = flag,
    country = country,
    cities = cities,
    startDate = LocalDate.parse(startDate),
    endDate = LocalDate.parse(endDate),
    photoCount = photoCnt,
    placeCount = placeCnt,
    badgeCount = badgeCnt,
    accentColor = accentColor.hexToColorLong(),
    coverTiles = coverTiles.map { CoverTile(it.uri) },
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt),
)
