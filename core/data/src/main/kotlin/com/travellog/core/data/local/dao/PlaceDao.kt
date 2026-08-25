package com.travellog.core.data.local.dao

import androidx.room.*
import com.travellog.core.data.local.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Query("SELECT * FROM places WHERE tripId = :tripId ORDER BY visitedAt ASC")
    fun getByTrip(tripId: String): Flow<List<PlaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: PlaceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(entities: List<PlaceEntity>)

    @Delete
    suspend fun delete(entity: PlaceEntity)
}
