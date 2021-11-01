package dev.marshi.jetmemo.ui.memolist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MemoListViewModel {

    val effect: Flow<MemoListEffect>
    val state: StateFlow<MemoListScreenState>

    fun navigateToDetail()
}
