package dev.marshi.jetmemo.ui.memolist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class FakeMemoListViewModel(
    state: MemoListScreenState
) : MemoListViewModel {

    override val effect: Flow<MemoListEffect> = flow { }
    override val state: StateFlow<MemoListScreenState> = MutableStateFlow(state)

    override fun navigateToDetail() {}
}
