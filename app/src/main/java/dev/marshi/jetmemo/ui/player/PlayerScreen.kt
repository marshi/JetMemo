package dev.marshi.jetmemo.ui.player

import android.app.Activity
import android.content.ComponentName
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.marshi.jetmemo.media.player.MediaPlaybackService
import dev.marshi.jetmemo.utils.extractActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = context.extractActivity()
    val lifecycleEventObserver = lifecycleEventObserver(activity, viewModel.controllerCallback)

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(key1 = Unit) {
        lifecycle.addObserver(lifecycleEventObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleEventObserver)
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect { event ->
            onReceiveEvent(event, activity)
        }
    }
    val playerState = viewModel.state.collectAsState(initial = PlayerState(PlayerState.Type.PLAY))
    Player(
        playerState = playerState.value,
        onStart = {
            viewModel.play()
        },
        onStop = {
            viewModel.stop()
        },
        onPause = {
            viewModel.pause()
        }
    )
}

private fun onReceiveEvent(event: PlayerEvent, activity: Activity) {
    val transportControls = MediaControllerCompat.getMediaController(activity).transportControls
    when (event) {
        PlayerEvent.Play -> transportControls.play()
        PlayerEvent.Pause -> transportControls.pause()
        PlayerEvent.Stop -> transportControls.stop()
    }
}

private fun lifecycleEventObserver(
    activity: Activity,
    mediaControllerCallback: MediaControllerCallback
): LifecycleEventObserver {
    lateinit var mediaBrowser: MediaBrowserCompat
    val lifecycleEventObserver = LifecycleEventObserver { owner, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                mediaBrowser = MediaBrowserCompat(
                    activity,
                    ComponentName(activity, MediaPlaybackService::class.java),
                    connectionCallback(activity, mediaControllerCallback) { mediaBrowser },
                    null
                )
            }
            Lifecycle.Event.ON_START -> mediaBrowser.connect()
            Lifecycle.Event.ON_STOP -> {
                MediaControllerCompat.getMediaController(activity)
                    .unregisterCallback(mediaControllerCallback)
                mediaBrowser.disconnect()
            }
            else -> {
            }
        }
    }
    return lifecycleEventObserver
}

private fun connectionCallback(
    activity: Activity,
    mediaControllerCallback: MediaControllerCallback,
    mediaBrowser: () -> MediaBrowserCompat,
): MediaBrowserCompat.ConnectionCallback {
    val subscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>
        ) {
            MediaControllerCompat.getMediaController(activity)?.transportControls?.prepare()
        }
    }

    val connectionCallback = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            // https://developer.android.google.cn/guide/topics/media-apps/audio-app/building-a-mediabrowser-client?hl=ja#connect-to-mediabrowserservice
            val mediaBrowser = mediaBrowser()
            val mediaController = MediaControllerCompat(activity, mediaBrowser.sessionToken)
            MediaControllerCompat.setMediaController(
                activity,
                mediaController
            )
            MediaControllerCompat.getMediaController(activity)
                .registerCallback(mediaControllerCallback)
            mediaBrowser.subscribe(mediaBrowser.root, subscriptionCallback)
        }
    }
    return connectionCallback
}