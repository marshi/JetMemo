package dev.marshi.jetmemo.ui.memodetail

import androidx.lifecycle.ViewModel
import dev.marshi.jetmemo.domain.entity.MemoId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeMemoDetailViewModel(
    state: MemoDetailScreenState
) : MemoDetailViewModel, ViewModel() {

    override val state: StateFlow<MemoDetailScreenState> = MutableStateFlow(state)
    override fun dispatch(event: MemoDetailViewModel.Event) {}
}
