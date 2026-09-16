package com.travellog.core.data.repository.impl

import com.travellog.core.data.mapper.toDomain
import com.travellog.core.data.mapper.toHexColor
import com.travellog.core.model.Memo
import com.travellog.core.model.repository.MemoRepository
import com.travellog.core.network.api.MemoApi
import com.travellog.core.network.dto.memo.CreateMemoRequest
import com.travellog.core.network.dto.memo.UpdateMemoRequest
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val memoApi: MemoApi,
) : MemoRepository {

    override suspend fun createMemo(tripId: String, text: String, city: String?, accentColor: Long?): Memo =
        memoApi.createMemo(CreateMemoRequest(tripId, text, city, accentColor?.toHexColor())).data.toDomain()

    override suspend fun updateMemo(memoId: String, text: String?, accentColor: Long?): Memo =
        memoApi.updateMemo(memoId, UpdateMemoRequest(text, accentColor?.toHexColor())).data.toDomain()

    override suspend fun deleteMemo(memoId: String) {
        memoApi.deleteMemo(memoId)
    }
}
