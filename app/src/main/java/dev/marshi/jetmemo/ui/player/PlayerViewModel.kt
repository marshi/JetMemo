package dev.marshi.jetmemo.ui.player

import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class PlayerViewModel : ViewModel() {

    val controllerCallback = MediaControllerCallback()

    val state = controllerCallback.playbackStateFlow.filterNotNull().map {
        when (it.state) {
            PlaybackStateCompat.STATE_PLAYING -> PlayerState(PlayerState.Type.PAUSE)
            PlaybackStateCompat.STATE_PAUSED -> PlayerState(PlayerState.Type.PLAY)
            PlaybackStateCompat.STATE_STOPPED -> PlayerState(PlayerState.Type.PLAY)
            else -> PlayerState(PlayerState.Type.PAUSE)
        }
    }
}