package dev.marshi.jetmemo.ui.memodetail

import dev.marshi.jetmemo.domain.entity.MemoId
import kotlinx.coroutines.flow.StateFlow

interface MemoDetailViewModel {

    val state: StateFlow<MemoDetailScreenState>

    fun saveMemo(id: MemoId? = null, text: String)

    fun textValueChanged(text: String)

    fun startRecording(fileName: String)

    fun stopRecording()
}
