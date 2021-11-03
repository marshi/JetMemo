package dev.marshi.jetmemo.ui.memodetail

import kotlinx.coroutines.flow.StateFlow

interface MemoDetailViewModel {

    val state: StateFlow<MemoDetailScreenState>

    fun saveNewMemo(text: String)

    fun textValueChanged(text: String)

    fun startRecording(fileName: String)

    fun stopRecording()
}
