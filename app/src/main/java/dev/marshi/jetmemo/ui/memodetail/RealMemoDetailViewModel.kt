package dev.marshi.jetmemo.ui.memodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.marshi.jetmemo.domain.entity.MemoId
import dev.marshi.jetmemo.domain.repository.MemoRepository
import dev.marshi.jetmemo.media.recorder.Recorder
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private var job: Job? = null

    fun init(memoId: MemoId) {
        observe(memoId = memoId)
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
                    saveMemo(state.value.memoId, state.value.text)
                    _effect.emit(MemoDetailViewModel.Effect.ShowSaveToast)
                }
                is MemoDetailViewModel.Event.ChangeText -> {
                    changeText(event.text)
                }
            }
        }
    }

    private fun observe(memoId: MemoId) {
        job?.cancel()
        job = memoRepository.observe(memoId).filterNotNull().onEach { memo ->
            _state.value = _state.value.copy(memoId = memoId, text = memo.text ?: "")
        }.launchIn(viewModelScope)
    }

    private fun changeText(text: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(text = text)
        }
    }

    private suspend fun saveMemo(id: MemoId?, text: String) {
        if (id == null) {
            val id = memoRepository.add(text)
            observe(MemoId.from(id))
        } else {
            memoRepository.update(id, text)
        }
    }
}
