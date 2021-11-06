package dev.marshi.jetmemo.ui.memolist

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeMemoListViewModel(
    state: MemoListScreenState
) : MemoListViewModel {

    override val state: StateFlow<MemoListScreenState> = MutableStateFlow(state)
}
