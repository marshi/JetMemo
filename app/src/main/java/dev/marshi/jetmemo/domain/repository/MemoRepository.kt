package dev.marshi.jetmemo.domain.repository

import dev.marshi.jetmemo.domain.entity.Memo
import kotlinx.coroutines.flow.Flow

interface MemoRepository {

    suspend fun add(text: String)
    fun list(): Flow<List<Memo>>
}
