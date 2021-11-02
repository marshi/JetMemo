package dev.marshi.jetmemo.ui.player

import android.app.Activity
import android.support.v4.media.session.MediaControllerCompat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import dev.marshi.jetmemo.utils.collectInLaunchedEffect
import dev.marshi.jetmemo.utils.extractActivity

@Composable
fun PlayerScreen() {
    val context = LocalContext.current
    val viewModel = playerViewModel()
    context.extractActivity()?.let { activity ->
        val lifecycleEventObserver = JetMemoLifecycleEventObserver(
            activity,
            viewModel.controllerCallback
        )
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        viewModel.event.collectInLaunchedEffect { event ->
            onReceiveEvent(event, activity)
        }
        DisposableEffect(key1 = Unit) {
            lifecycle.addObserver(lifecycleEventObserver)
            onDispose {
                lifecycle.removeObserver(lifecycleEventObserver)
                MediaControllerCompat.getMediaController(activity).transportControls.stop()
            }
        }
    }
    val playerState = viewModel.state.collectAsState(initial = PlayerState(PlayerState.Type.PLAY))
    Player(
        playerState = playerState.value,
        onStart = { viewModel.play() },
        onPause = { viewModel.pause() }
    )
}

private fun onReceiveEvent(event: PlayerEvent, activity: Activity) {
    val transportControls = MediaControllerCompat.getMediaController(activity).transportControls
    when (event) {
        PlayerEvent.Play -> transportControls.play()
        PlayerEvent.Pause -> transportControls.pause()
    }
}

@Preview
@Composable
fun PlayerScreenPreview() {
    CompositionLocalProvider(
        providePlayerViewModel { FakePlayerViewModel(playerState = PlayerState()) }
    ) {
        PlayerScreen()
    }
}
