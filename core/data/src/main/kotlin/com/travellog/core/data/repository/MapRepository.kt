package com.travellog.core.data.repository

import com.travellog.core.model.FriendMapPin
import com.travellog.core.model.MapPin

interface MapRepository {
    suspend fun getMyPins(): List<MapPin>
    suspend fun getFriendPins(): List<FriendMapPin>
}
