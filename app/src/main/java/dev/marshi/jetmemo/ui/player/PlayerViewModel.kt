package dev.marshi.jetmemo.ui.player

import android.support.v4.media.session.PlaybackStateCompat
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