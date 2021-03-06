package dev.marshi.jetmemo.ui.memolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.marshi.jetmemo.domain.repository.MemoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealMemoListViewModel @Inject constructor(
    memoRepository: MemoRepository
) : MemoListViewModel, ViewModel() {

    private val memos = memoRepository.list()
    override val state: StateFlow<MemoListScreenState> = memos
        .map { memos ->
            MemoListScreenState(memos.sortedBy { -it.insertedAt })
        }.stateIn(
            viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = MemoListScreenState.INITIAL
        )

    init {
        viewModelScope.launch {
            memos.collect {
                println(it)
            }
        }
    }
}
