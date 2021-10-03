package dev.marshi.jetmemo.data.database.datasource

import dev.marshi.jetmemo.data.database.AppDatabase
import dev.marshi.jetmemo.data.database.entity.MemoEntity
import dev.marshi.jetmemo.domain.repository.MemoRepository
import javax.inject.Inject

class MemoDatasource @Inject constructor(
    private val db: AppDatabase
) : MemoRepository {

    override suspend fun add(text: String) {
        val entity = MemoEntity.from(text)
        db.memoDao().insert(entity)
    }
}