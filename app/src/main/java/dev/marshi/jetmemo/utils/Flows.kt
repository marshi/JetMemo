package dev.marshi.jetmemo.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectInLaunchedEffect(block: suspend (T) -> Unit) {
    LaunchedEffect(key1 = this) {
        collect(block)
    }
}
