package com.travellog.feature.map.model

import com.travellog.core.model.Badge

data class TravelGlobeData(
    val countryCode: String,
    val travelCount: Int,
    val badgeList: List<Badge>
) {
}