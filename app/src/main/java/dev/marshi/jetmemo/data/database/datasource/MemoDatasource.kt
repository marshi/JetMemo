package dev.marshi.jetmemo.data.database.datasource

import dev.marshi.jetmemo.data.database.AppDatabase
import dev.marshi.jetmemo.data.database.entity.MemoEntity
import dev.marshi.jetmemo.domain.entity.Memo
import dev.marshi.jetmemo.domain.entity.MemoId
import dev.marshi.jetmemo.domain.repository.MemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemoDatasource @Inject constructor(
    private val db: AppDatabase
) : MemoRepository {
    override suspend fun update(memoId: MemoId, text: String) {
        val entity = MemoEntity.from(id = memoId.value, text = text)
        db.memoDao().update(entity)
    }

    override suspend fun add(text: String): Long {
        val entity = MemoEntity.from(text = text)
        return db.memoDao().insert(entity)
    }

    override fun list(): Flow<List<Memo>> {
        return db.memoDao().selectAll().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun find(memoId: MemoId): Memo? {
        return db.memoDao().select(memoId.value)?.toDomain()
    }

    override fun observe(memoId: MemoId): Flow<Memo?> {
        return db.memoDao().observe(memoId.value).map { it?.toDomain() }
    }
}