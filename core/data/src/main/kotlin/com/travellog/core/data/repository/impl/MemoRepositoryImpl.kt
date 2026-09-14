package com.travellog.core.data.repository.impl

import com.travellog.core.data.local.dao.MemoDao
import com.travellog.core.data.mapper.toDomain
import com.travellog.core.data.mapper.toEntity
import com.travellog.core.data.repository.MemoRepository
import com.travellog.core.model.Memo
import com.travellog.core.network.api.MemoApi
import com.travellog.core.network.dto.memo.CreateMemoRequest
import com.travellog.core.network.dto.memo.UpdateMemoRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val memoApi: MemoApi,
    private val memoDao: MemoDao,
) : MemoRepository {

    override fun observeMemosByTrip(tripId: String): Flow<List<Memo>> =
        memoDao.getByTrip(tripId).map { list -> list.map { it.toDomain() } }

    override suspend fun createMemo(tripId: String, text: String, city: String?, accentColor: String?): Memo {
        val memo = memoApi.createMemo(CreateMemoRequest(tripId, text, city, accentColor)).data
        memoDao.upsert(memo.toEntity())
        return memo.toDomain()
    }

    override suspend fun updateMemo(memoId: String, text: String?, accentColor: String?): Memo {
        val memo = memoApi.updateMemo(memoId, UpdateMemoRequest(text, accentColor)).data
        memoDao.upsert(memo.toEntity())
        return memo.toDomain()
    }

    override suspend fun deleteMemo(memoId: String) {
        memoApi.deleteMemo(memoId)
        memoDao.deleteById(memoId)
    }
}
