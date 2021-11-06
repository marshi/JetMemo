package dev.marshi.jetmemo.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.marshi.jetmemo.domain.entity.Memo
import dev.marshi.jetmemo.domain.entity.MemoId

@Entity(tableName = "memo")
data class MemoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "inserted_at") val insertedAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
) {
    companion object {
        fun from(id: Long? = null, text: String): MemoEntity {
            val now = System.currentTimeMillis()
            return MemoEntity(
                id = id ?: 0L,
                text = text,
                insertedAt = now,
                updatedAt = now,
            )
        }
    }

    fun toDomain(): Memo {
        return Memo(
            id = MemoId.from(id),
            text = text,
            insertedAt = insertedAt,
            updatedAt = System.currentTimeMillis()
        )
    }
}