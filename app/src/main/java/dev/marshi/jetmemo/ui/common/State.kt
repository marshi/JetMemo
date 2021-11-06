package dev.marshi.jetmemo.ui.common

sealed class State {
    class Success<T>(val value: T) : State()
    class Error(val e: Exception) : State()
    object Loading : State()
}
