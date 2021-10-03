package dev.marshi.jetmemo.ui.memodetail

data class MemoDetailScreenState(
    val text: String
) {
    companion object {
        val INITIAL = MemoDetailScreenState(
            text = ""
        )
    }
}
