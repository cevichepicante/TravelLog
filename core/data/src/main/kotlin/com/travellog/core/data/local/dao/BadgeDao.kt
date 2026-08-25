package com.travellog.core.data.local.dao

import androidx.room.*
import com.travellog.core.data.local.entity.BadgeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BadgeDao {
    @Query("SELECT * FROM badges ORDER BY earnedAt DESC")
    fun getAll(): Flow<List<BadgeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: BadgeEntity)

    @Delete
    suspend fun delete(entity: BadgeEntity)
}
