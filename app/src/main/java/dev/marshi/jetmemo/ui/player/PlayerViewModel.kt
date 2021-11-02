package dev.marshi.jetmemo.ui.player

import android.support.v4.media.session.PlaybackStateCompat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface PlayerViewModel {

    val controllerCallback: MediaControllerCallback
    val event: Flow<PlayerEvent>
    val state: Flow<PlayerState>

    fun play()

    fun pause()
}

private val LocalPlayerViewModelFactory = compositionLocalOf<@Composable () -> PlayerViewModel> {
    {
        error("not LocalPlayerViewModel provided")
    }
}

fun providePlayerViewModel(viewModelFactory: @Composable () -> PlayerViewModel) =
    LocalPlayerViewModelFactory provides viewModelFactory

@Composable
fun playerViewModel() = LocalPlayerViewModelFactory.current()