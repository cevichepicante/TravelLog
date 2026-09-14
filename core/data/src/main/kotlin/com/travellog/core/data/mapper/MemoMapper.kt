package com.travellog.core.data.mapper

import com.travellog.core.data.local.entity.MemoEntity
import com.travellog.core.model.Memo
import com.travellog.core.network.dto.memo.MemoDto
import java.time.Instant

fun MemoDto.toEntity(): MemoEntity = MemoEntity(
    id = id,
    tripId = tripId,
    photoId = null,
    text = text,
    city = city ?: "",
    accentColor = accentColor.hexToColorLong(),
    createdAt = Instant.parse(createdAt),
)

fun MemoEntity.toDomain(): Memo = Memo(
    id = id,
    tripId = tripId,
    photoId = photoId,
    text = text,
    city = city,
    accentColor = accentColor,
    createdAt = createdAt,
)

fun MemoDto.toDomain(): Memo = Memo(
    id = id,
    tripId = tripId,
    photoId = null,
    text = text,
    city = city ?: "",
    accentColor = accentColor.hexToColorLong(),
    createdAt = Instant.parse(createdAt),
)
