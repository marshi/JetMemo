package dev.marshi.jetmemo.domain.entity

class Memo(
    val id: Int,
    private val text: String?
) {
    val textOrDefault = text ?: "メモが未記入です"
}
