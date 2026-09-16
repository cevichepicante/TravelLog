package com.travellog.core.model.repository

import com.travellog.core.model.Memo

interface MemoRepository {
    suspend fun createMemo(tripId: String, text: String, city: String? = null, accentColor: Long? = null): Memo
    suspend fun updateMemo(memoId: String, text: String? = null, accentColor: Long? = null): Memo
    suspend fun deleteMemo(memoId: String)
}
