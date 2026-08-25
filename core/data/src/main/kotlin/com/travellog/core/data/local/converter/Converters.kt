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

    @TypeConverter fun fromBadgeType(value: BadgeType?): String? = value?.name
    @TypeConverter fun toBadgeType(value: String?): BadgeType? = value?.let { BadgeType.valueOf(it) }

    @TypeConverter fun fromVisibility(value: Visibility?): String? = value?.name
    @TypeConverter fun toVisibility(value: String?): Visibility? = value?.let { Visibility.valueOf(it) }
}
