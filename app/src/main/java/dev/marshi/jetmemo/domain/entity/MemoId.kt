package dev.marshi.jetmemo.domain.entity

@JvmInline
value class MemoId private constructor(
    val value: Long
) {
    companion object {
        fun from(value: Long): MemoId {
            return MemoId(value)
        }
    }
}
