package dev.marshi.jetmemo.ui.memodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.marshi.jetmemo.domain.entity.MemoId
import dev.marshi.jetmemo.domain.repository.MemoRepository
import dev.marshi.jetmemo.media.recorder.Recorder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
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
    private val _effect = MutableSharedFlow<MemoDetailViewModel.Effect>()
    override val effect: Flow<MemoDetailViewModel.Effect> = _effect

    fun init(memoId: MemoId) {
        viewModelScope.launch {
            val memo = memoRepository.find(memoId) ?: return@launch
            _state.value = _state.value.copy(memoId = memoId, text = memo.textOrDefault)
        }
    }

    override fun dispatch(event: MemoDetailViewModel.Event) {
        viewModelScope.launch {
            when (event) {
                is MemoDetailViewModel.Event.StartRecording -> {
                    recorder.start(event.fileName)
                }
                MemoDetailViewModel.Event.StopRecording -> {
                    recorder.stop()
                }
                is MemoDetailViewModel.Event.SaveMemo -> {
                    saveMemo(event.id, event.text)
                    _effect.emit(MemoDetailViewModel.Effect.ShowSaveToast)
                }
                is MemoDetailViewModel.Event.ChangeText -> {
                    changeText(event.text)
                }
            }
        }
    }

    private fun changeText(text: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(text = text)
        }
    }

    private fun saveMemo(id: MemoId?, text: String) {
        viewModelScope.launch {
            if (id == null) {
                memoRepository.add(text)
            } else {
                memoRepository.update(id, text)
            }
        }
    }
}
