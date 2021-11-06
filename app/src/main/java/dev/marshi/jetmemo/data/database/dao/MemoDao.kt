package dev.marshi.jetmemo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.marshi.jetmemo.data.database.entity.MemoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {

    @Insert
    suspend fun insert(memoEntity: MemoEntity): Long

    @Update
    suspend fun update(memoEntity: MemoEntity)

    @Query("select * from memo")
    fun selectAll(): Flow<List<MemoEntity>>

    @Query("select * from memo where id = :id")
    suspend fun select(id: Long): MemoEntity?

    @Query("select * from memo where id = :id")
    fun observe(id: Long): Flow<MemoEntity?>
}
