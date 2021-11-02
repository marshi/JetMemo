package dev.marshi.jetmemo.domain.entity

@JvmInline
value class MemoId private constructor(
    val value: Int
) {
    companion object {
        fun from(value: Int): MemoId {
            return MemoId(value)
        }
    }
}
