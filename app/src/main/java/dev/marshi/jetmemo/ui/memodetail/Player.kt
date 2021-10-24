package dev.marshi.jetmemo.ui.memodetail

import android.content.ComponentName
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import dev.marshi.jetmemo.media.player.MediaPlaybackService
import dev.marshi.jetmemo.utils.extractActivity

@Composable
fun Player(
    onStart: () -> Unit,
    onStop: () -> Unit,
    onPause: () -> Unit
) {

    val context = LocalContext.current
    val activity = context.extractActivity()
    lateinit var mediaBrowser: MediaBrowserCompat

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
            MediaControllerCompat.setMediaController(
                activity,
                MediaControllerCompat(context, mediaBrowser.sessionToken)
            )
            mediaBrowser.subscribe(mediaBrowser.root, subscriptionCallback)
        }
    }

    val observer = remember {
        LifecycleEventObserver { owner, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    mediaBrowser = MediaBrowserCompat(
                        activity,
                        ComponentName(activity, MediaPlaybackService::class.java),
                        connectionCallback,
                        null
                    )
                }
                Lifecycle.Event.ON_START -> mediaBrowser.connect()
                Lifecycle.Event.ON_STOP -> {
//                    MediaControllerCompat.getMediaController(activity).unregisterCallback()
                    mediaBrowser.disconnect()
                }
                else -> {
                }
            }
        }
    }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(key1 = Unit) {
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    Surface {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                onClick = onPause
            ) {
                Text("一時停止")
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .wrapContentWidth()
                    .weight(1f),
                onClick = onStart
            ) {
                Icon(Icons.Filled.PlayArrow, null)
            }
            Button(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                onClick = onStop
            ) {
                Text("停止")
            }
        }
    }
}

@Preview
@Composable
fun PlayerPreview() {
    Player(
        onStart = {},
        onPause = {},
        onStop = {},
    )
}
