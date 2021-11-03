package dev.marshi.jetmemo.ui.memodetail

import dev.marshi.jetmemo.domain.entity.MemoId

data class MemoDetailScreenState(
    val memoId: MemoId?,
    val text: String
) {
    companion object {
        val INITIAL = MemoDetailScreenState(
            memoId = null,
            text = ""
        )
    }
}