package com.travellog.core.data.repository.impl

import com.travellog.core.data.mapper.toDomain
import com.travellog.core.model.Badge
import com.travellog.core.model.BadgeDesign
import com.travellog.core.model.repository.BadgeRepository
import com.travellog.core.network.api.BadgeApi
import com.travellog.core.network.dto.badge.UpdateBadgeDesignRequest
import javax.inject.Inject

class BadgeRepositoryImpl @Inject constructor(
    private val badgeApi: BadgeApi,
) : BadgeRepository {

    override suspend fun getBadge(badgeId: String): Badge =
        badgeApi.getBadge(badgeId).data.toDomain()

    override suspend fun getBadgeDesigns(): List<BadgeDesign> =
        badgeApi.getBadgeDesigns().data.items.map { it.toDomain() }

    override suspend fun updateBadgeDesign(badgeId: String, designId: String): Badge =
        badgeApi.updateBadgeDesign(badgeId, UpdateBadgeDesignRequest(designId)).data.toDomain()
}
