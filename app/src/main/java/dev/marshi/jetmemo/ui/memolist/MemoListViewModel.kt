package dev.marshi.jetmemo.ui.memolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.marshi.jetmemo.domain.repository.MemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoListViewModel @Inject constructor(
    private val memoRepository: MemoRepository
) : ViewModel() {

    private val memos = memoRepository.list()
    private var _effect = MutableSharedFlow<MemoListEffect>()
    val effect: Flow<MemoListEffect> = _effect

    init {
        viewModelScope.launch {
            memos.collect {
                println(it)
            }
        }
    }

    val state: StateFlow<MemoListScreenState> = memos
        .map { memos ->
            MemoListScreenState(memos)
        }.stateIn(
            viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = MemoListScreenState.INITIAL
        )

    fun navigateToDetail() {
        viewModelScope.launch {
            _effect.emit(MemoListEffect.NavigateToDetail)
        }
    }
}
