package dev.marshi.jetmemo.domain.entity

class Memo(
    val id: MemoId,
    val text: String?,
    val insertedAt: Long,
    val updatedAt: Long,
) {
    val textOrDefault = text ?: "メモが未記入です"
}
