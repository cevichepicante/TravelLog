package com.travellog.core.data.local.dao

import androidx.room.*
import com.travellog.core.data.local.entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedules ORDER BY createdAt DESC")
    fun getAll(): Flow<List<ScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ScheduleEntity)

    @Delete
    suspend fun delete(entity: ScheduleEntity)
}
