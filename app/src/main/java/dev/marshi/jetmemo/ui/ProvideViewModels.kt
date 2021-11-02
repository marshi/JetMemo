package dev.marshi.jetmemo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import dev.marshi.jetmemo.ui.player.RealPlayerViewModel
import dev.marshi.jetmemo.ui.player.providePlayerViewModel

@Composable
fun ProvideViewModels(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        providePlayerViewModel { hiltViewModel<RealPlayerViewModel>() }
    ) {
        content()
    }
}