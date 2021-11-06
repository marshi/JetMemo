package dev.marshi.jetmemo.ui.memodetail

import dev.marshi.jetmemo.domain.entity.MemoId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MemoDetailViewModel {

    val state: StateFlow<MemoDetailScreenState>
    val effect: Flow<Effect>

    fun dispatch(event: Event)

    sealed class Event {
        class StartRecording(val fileName: String) : Event()
        object StopRecording : Event()
        class SaveMemo() : Event()
        class ChangeText(val text: String) : Event()
    }

    sealed class Effect {
        object ShowSaveToast : Effect()
    }
}
