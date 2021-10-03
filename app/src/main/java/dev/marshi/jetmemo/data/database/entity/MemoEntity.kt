package dev.marshi.jetmemo.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "text") val text: String?
) {
    companion object {
        fun from(text: String): MemoEntity {
            return MemoEntity(
                id = 0,
                text = text,
            )
        }
    }
}