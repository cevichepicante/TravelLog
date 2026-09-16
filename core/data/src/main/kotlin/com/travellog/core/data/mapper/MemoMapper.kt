package com.travellog.core.data.mapper

import com.travellog.core.model.Memo
import com.travellog.core.network.dto.memo.MemoDto
import java.time.Instant

fun MemoDto.toDomain(order: Int = 0): Memo = Memo(
    id = id,
    tripId = tripId,
    order = order,
    text = text,
    city = city,
    accentColor = accentColor?.hexToColorLong(),
    createdAt = Instant.parse(createdAt),
)
