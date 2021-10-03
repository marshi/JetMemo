package dev.marshi.jetmemo.ui.memolist

import dev.marshi.jetmemo.domain.entity.Memo

data class MemoListScreenState(
    val memos: List<Memo>
) {
    companion object {
        val INITIAL = MemoListScreenState(listOf())
    }
}