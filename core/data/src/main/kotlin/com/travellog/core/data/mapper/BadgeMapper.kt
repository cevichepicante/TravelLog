package com.travellog.core.data.mapper

import com.travellog.core.model.Badge
import com.travellog.core.model.BadgeDesign
import com.travellog.core.model.BadgeType
import com.travellog.core.network.dto.badge.BadgeDesignDto
import com.travellog.core.network.dto.badge.BadgeDto
import java.time.Instant

fun BadgeDto.toDomain(): Badge = Badge(
    id = id,
    type = when (type) {
        "COUNTRY" -> BadgeType.Country(id = countryId ?: "", name = name)
        "STATE" -> BadgeType.State(countryId = countryId ?: "", name = name)
        else -> BadgeType.Country(id = countryId ?: "", name = name)
    },
    emoji = emoji,
    label = label,
    isRare = isRare,
    designId = designId,
    earnedAt = Instant.parse(earnedAt),
)

fun BadgeDesignDto.toDomain(): BadgeDesign = BadgeDesign(
    id = id,
    name = name,
    previewEmoji = previewEmoji,
    priceKrw = priceKrw,
    isPurchased = isPurchased,
)
