package dev.marshi.jetmemo.ui.memodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    override fun saveNewMemo(text: String) {
        viewModelScope.launch {
            memoRepository.add(text)
        }
    }

    override fun startRecording(fileName: String) {
        recorder.start("record_1")
    }

    override fun stopRecording() {
        recorder.stop()
    }
}
