package dev.marshi.jetmemo.ui.player

import android.app.Activity
import android.content.ComponentName
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import dev.marshi.jetmemo.media.player.MediaPlaybackService

class JetMemoLifecycleEventObserver(
    private val activity: Activity,
    private val mediaControllerCallback: MediaControllerCallback
) : LifecycleEventObserver {

    lateinit var mediaBrowser: MediaBrowserCompat
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                mediaBrowser = MediaBrowserCompat(
                    activity,
                    ComponentName(activity, MediaPlaybackService::class.java),
                    ConnectionCallback(activity, mediaControllerCallback) { mediaBrowser },
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
}
