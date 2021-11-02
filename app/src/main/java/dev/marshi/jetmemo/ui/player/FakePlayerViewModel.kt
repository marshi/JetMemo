package dev.marshi.jetmemo.ui.player

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

class FakePlayerViewModel(
    playerState: PlayerState,
) : PlayerViewModel {

    override val controllerCallback: MediaControllerCallback = object : MediaControllerCallback() {}
    override val event: SharedFlow<PlayerEvent> = MutableSharedFlow()
    override val state: Flow<PlayerState> = MutableStateFlow(playerState)

    override fun play() {}

    override fun pause() {}
}