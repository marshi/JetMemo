package dev.marshi.jetmemo.ui.memodetail

import dev.marshi.jetmemo.domain.entity.MemoId
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MemoDetailViewModel {

    val state: StateFlow<MemoDetailScreenState>

    fun dispatch(event: Event)

    sealed class Event {
        class StartRecording(val fileName: String) : Event()
        object StopRecording : Event()
        class SaveMemo(val id: MemoId?, val text: String) : Event()
        class ChangeText(val text: String) : Event()
    }
}
