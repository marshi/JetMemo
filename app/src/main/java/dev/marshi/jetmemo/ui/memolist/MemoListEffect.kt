package dev.marshi.jetmemo.ui.memolist

sealed class MemoListEffect {

    object NavigateToDetail : MemoListEffect()
}