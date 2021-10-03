package dev.marshi.jetmemo.ui.memodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.marshi.jetmemo.domain.repository.MemoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoDetailViewModel @Inject constructor(
    private val memoRepository: MemoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MemoDetailScreenState.INITIAL)
    val state: StateFlow<MemoDetailScreenState> = _state

    fun saveNewMemo(text: String) {
        viewModelScope.launch {
            memoRepository.add(text)
        }
    }
}

