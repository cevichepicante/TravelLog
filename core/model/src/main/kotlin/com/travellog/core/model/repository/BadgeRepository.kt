package com.travellog.core.model.repository

import com.travellog.core.model.Badge
import com.travellog.core.model.BadgeDesign

interface BadgeRepository {
    suspend fun getBadge(badgeId: String): Badge
    suspend fun getBadgeDesigns(): List<BadgeDesign>
    suspend fun updateBadgeDesign(badgeId: String, designId: String): Badge
}
