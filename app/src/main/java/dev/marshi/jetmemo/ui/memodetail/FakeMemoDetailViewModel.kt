package dev.marshi.jetmemo.ui.memodetail

import androidx.lifecycle.ViewModel
import dev.marshi.jetmemo.domain.entity.MemoId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class FakeMemoDetailViewModel(
    state: MemoDetailScreenState
) : MemoDetailViewModel, ViewModel() {

    override val state: StateFlow<MemoDetailScreenState> = MutableStateFlow(state)
    override val effect: Flow<MemoDetailViewModel.Effect> = flow {}

    override fun dispatch(event: MemoDetailViewModel.Event) {}
}
