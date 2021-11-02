package dev.marshi.jetmemo.ui.player

import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow

open class MediaControllerCallback() : MediaControllerCompat.Callback() {
    private val _playbackStateFlow = MutableStateFlow<PlaybackStateCompat?>(null)
    val playbackStateFlow: StateFlow<PlaybackStateCompat?> = _playbackStateFlow

    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
        _playbackStateFlow.value = state
    }
}