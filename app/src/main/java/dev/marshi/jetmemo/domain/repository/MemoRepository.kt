package dev.marshi.jetmemo.domain.repository

import dagger.internal.MemoizedSentinel
import dev.marshi.jetmemo.domain.entity.Memo
import dev.marshi.jetmemo.domain.entity.MemoId
import kotlinx.coroutines.flow.Flow

interface MemoRepository {

    suspend fun update(memoId: MemoId, text: String)
    suspend fun add(text: String): Long
    fun list(): Flow<List<Memo>>
    suspend fun find(memoId: MemoId): Memo?
    fun observe(memoId: MemoId): Flow<Memo?>
}
