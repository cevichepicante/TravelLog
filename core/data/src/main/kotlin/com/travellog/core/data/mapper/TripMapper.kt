package com.travellog.core.data.mapper

import com.travellog.core.model.CoverTile
import com.travellog.core.model.TimelineItem
import com.travellog.core.model.Trip
import com.travellog.core.network.dto.trip.TimelineItemDto
import com.travellog.core.network.dto.trip.TripDto
import java.time.Instant
import java.time.LocalDate

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

fun TimelineItemDto.toDomain(order: Int = 0): TimelineItem {
    val timestamp = Instant.parse(timestamp)
    return when (type) {
        "PHOTO" -> TimelineItem.PhotoItem(timestamp, requireNotNull(photo) { "timeline PHOTO item missing photo" }.toDomain(order))
        "PLACE" -> TimelineItem.PlaceItem(timestamp, requireNotNull(place) { "timeline PLACE item missing place" }.toDomain(order))
        "MEMO" -> TimelineItem.MemoItem(timestamp, requireNotNull(memo) { "timeline MEMO item missing memo" }.toDomain(order))
        else -> error("Unknown timeline item type: $type")
    }
}
