package com.travellog.core.data.repository

import com.travellog.core.model.Memo
import kotlinx.coroutines.flow.Flow

interface MemoRepository {
    fun observeMemosByTrip(tripId: String): Flow<List<Memo>>
    suspend fun createMemo(tripId: String, text: String, city: String? = null, accentColor: String? = null): Memo
    suspend fun updateMemo(memoId: String, text: String? = null, accentColor: String? = null): Memo
    suspend fun deleteMemo(memoId: String)
}
