package dev.marshi.jetmemo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import dev.marshi.jetmemo.data.database.entity.MemoEntity

@Dao
interface MemoDao {

    @Insert
    suspend fun insert(memoEntity: MemoEntity)
}
