package dev.marshi.jetmemo.ui.memolist

import kotlinx.coroutines.flow.StateFlow

interface MemoListViewModel {

    val state: StateFlow<MemoListScreenState>
}
