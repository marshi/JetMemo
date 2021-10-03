package dev.marshi.jetmemo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.marshi.jetmemo.data.database.dao.MemoDao
import dev.marshi.jetmemo.data.database.entity.MemoEntity

@Database(
    entities = arrayOf(
        MemoEntity::class,
    ),
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun memoDao(): MemoDao
}