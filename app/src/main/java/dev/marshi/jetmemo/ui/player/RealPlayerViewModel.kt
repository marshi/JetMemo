package dev.marshi.jetmemo.ui.player

import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RealPlayerViewModel : ViewModel(), PlayerViewModel {

    override val controllerCallback = MediaControllerCallback()
    private val _event = MutableSharedFlow<PlayerEvent>()
    override val event: SharedFlow<PlayerEvent> = _event

    override val state = controllerCallback.playbackStateFlow.filterNotNull().map {
        when (it.state) {
            PlaybackStateCompat.STATE_PLAYING -> PlayerState(PlayerState.Type.PAUSE)
            PlaybackStateCompat.STATE_PAUSED -> PlayerState(PlayerState.Type.PLAY)
            PlaybackStateCompat.STATE_STOPPED -> PlayerState(PlayerState.Type.PLAY)
            else -> PlayerState(PlayerState.Type.PAUSE)
        }
    }

    override fun play() {
        viewModelScope.launch {
            _event.emit(PlayerEvent.Play)
        }
    }

    override fun pause() {
        viewModelScope.launch {
            _event.emit(PlayerEvent.Pause)
        }
    }
}