package dev.marshi.jetmemo.ui.memodetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeMemoDetailViewModel(
    state: MemoDetailScreenState
) : MemoDetailViewModel, ViewModel() {

    override val state: StateFlow<MemoDetailScreenState> = MutableStateFlow(state)

    override fun saveNewMemo(text: String) {}

    override fun startRecording(fileName: String) {}

    override fun stopRecording() {}
}
