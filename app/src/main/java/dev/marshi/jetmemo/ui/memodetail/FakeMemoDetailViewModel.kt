package dev.marshi.jetmemo.ui.memodetail

import androidx.lifecycle.ViewModel
import dev.marshi.jetmemo.domain.entity.MemoId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeMemoDetailViewModel(
    state: MemoDetailScreenState
) : MemoDetailViewModel, ViewModel() {

    override val state: StateFlow<MemoDetailScreenState> = MutableStateFlow(state)
    override fun saveMemo(id: MemoId?, text: String) {}
    override fun textValueChanged(text: String) {}
    override fun startRecording(fileName: String) {}
    override fun stopRecording() {}
}
