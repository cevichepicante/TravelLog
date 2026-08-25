package com.travellog.core.data.local.dao

import androidx.room.*
import com.travellog.core.data.local.entity.MemoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query("SELECT * FROM memos WHERE tripId = :tripId ORDER BY createdAt ASC")
    fun getByTrip(tripId: String): Flow<List<MemoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: MemoEntity)

    @Delete
    suspend fun delete(entity: MemoEntity)
}
