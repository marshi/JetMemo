package dev.marshi.jetmemo.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.marshi.jetmemo.domain.entity.Memo
import dev.marshi.jetmemo.domain.entity.MemoId

@Entity(tableName = "memo")
data class MemoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "text") val text: String?
) {
    companion object {
        fun from(id: Int? = null, text: String): MemoEntity {
            return MemoEntity(
                id = id ?: 0,
                text = text,
            )
        }
    }

    fun toDomain(): Memo {
        return Memo(
            id = MemoId.from(id),
            text = text
        )
    }
}