package dev.marshi.jetmemo.data.database.datasource

import dev.marshi.jetmemo.data.database.AppDatabase
import dev.marshi.jetmemo.data.database.entity.MemoEntity
import dev.marshi.jetmemo.domain.entity.Memo
import dev.marshi.jetmemo.domain.repository.MemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemoDatasource @Inject constructor(
    private val db: AppDatabase
) : MemoRepository {

    override suspend fun add(text: String) {
        val entity = MemoEntity.from(text)
        db.memoDao().insert(entity)
    }

    override fun list(): Flow<List<Memo>> {
        return db.memoDao().selectAll().map { list -> list.map { it.toDomain() } }
    }
}