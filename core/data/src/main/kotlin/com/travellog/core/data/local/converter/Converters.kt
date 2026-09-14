package com.travellog.core.data.local.converter

import androidx.room.TypeConverter
import com.travellog.core.model.BadgeType
import com.travellog.core.model.MediaType
import com.travellog.core.model.Visibility
import java.time.Instant
import java.time.LocalDate

class Converters {
    @TypeConverter fun fromInstant(value: Instant?): Long? = value?.toEpochMilli()
    @TypeConverter fun toInstant(value: Long?): Instant? = value?.let { Instant.ofEpochMilli(it) }

    @TypeConverter fun fromLocalDate(value: LocalDate?): String? = value?.toString()
    @TypeConverter fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter fun fromStringList(value: List<String>?): String? = value?.joinToString(",")
    @TypeConverter fun toStringList(value: String?): List<String> =
        if (value.isNullOrBlank()) emptyList() else value.split(",")

    @TypeConverter fun fromMediaType(value: MediaType?): String? = value?.name
    @TypeConverter fun toMediaType(value: String?): MediaType? = value?.let { MediaType.valueOf(it) }

    @TypeConverter
    fun fromBadgeType(value: BadgeType?): String? = when (value) {
        is BadgeType.Country -> "COUNTRY:${value.id}:${value.name}"
        is BadgeType.State -> "STATE:${value.countryId}:${value.name}"
        null -> null
    }

    @TypeConverter
    fun toBadgeType(value: String?): BadgeType? {
        if (value == null) return null
        val parts = value.split(":", limit = 3)
        return when (parts.getOrNull(0)) {
            "COUNTRY" -> BadgeType.Country(id = parts.getOrElse(1) { "" }, name = parts.getOrElse(2) { "" })
            "STATE" -> BadgeType.State(countryId = parts.getOrElse(1) { "" }, name = parts.getOrElse(2) { "" })
            else -> null
        }
    }

    @TypeConverter fun fromVisibility(value: Visibility?): String? = value?.name
    @TypeConverter fun toVisibility(value: String?): Visibility? = value?.let { Visibility.valueOf(it) }
}
