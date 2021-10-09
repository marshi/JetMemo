package dev.marshi.jetmemo.ui.memodetail

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.marshi.jetmemo.domain.repository.MemoRepository
import dev.marshi.jetmemo.recorder.Recorder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoDetailViewModel @Inject constructor(
    private val memoRepository: MemoRepository,
    private val recorder: Recorder
) : ViewModel() {

    private val _state = MutableStateFlow(MemoDetailScreenState.INITIAL)
    val state: StateFlow<MemoDetailScreenState> = _state

    fun saveNewMemo(text: String) {
        viewModelScope.launch {
            memoRepository.add(text)
        }
    }

    fun startRecording(fileName: String) {
        recorder.start("record_1")
    }

    fun stopRecording() {
        recorder.stop()
    }
}
