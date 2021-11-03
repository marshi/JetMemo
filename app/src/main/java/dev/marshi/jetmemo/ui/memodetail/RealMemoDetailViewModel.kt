package dev.marshi.jetmemo.ui.memodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.marshi.jetmemo.domain.entity.MemoId
import dev.marshi.jetmemo.domain.repository.MemoRepository
import dev.marshi.jetmemo.media.recorder.Recorder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealMemoDetailViewModel @Inject constructor(
    private val memoRepository: MemoRepository,
    private val recorder: Recorder
) : MemoDetailViewModel, ViewModel() {

    private val _state = MutableStateFlow(MemoDetailScreenState.INITIAL)
    override val state: StateFlow<MemoDetailScreenState> = _state

    fun init(memoId: MemoId) {
        viewModelScope.launch {
            val memo = memoRepository.find(memoId) ?: return@launch
            _state.value = _state.value.copy(memoId = memoId, text = memo.textOrDefault)
        }
    }

    override fun textValueChanged(text: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(text = text)
        }
    }

    override fun saveMemo(id: MemoId?, text: String) {
        viewModelScope.launch {
            if (id == null) {
                memoRepository.add(text)
            } else {
                memoRepository.update(id, text)
            }
        }
    }

    override fun startRecording(fileName: String) {
        recorder.start("record_1")
    }

    override fun stopRecording() {
        recorder.stop()
    }
}
