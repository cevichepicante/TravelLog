package com.travellog.core.data.repository.impl

import com.travellog.core.data.mapper.toDomain
import com.travellog.core.data.repository.MapRepository
import com.travellog.core.model.FriendMapPin
import com.travellog.core.model.MapPin
import com.travellog.core.network.api.MapApi
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapApi: MapApi,
) : MapRepository {

    override suspend fun getMyPins(): List<MapPin> =
        mapApi.getMyPins().data.items.map { it.toDomain() }

    override suspend fun getFriendPins(): List<FriendMapPin> =
        mapApi.getFriendPins().data.items.map { it.toDomain() }
}
