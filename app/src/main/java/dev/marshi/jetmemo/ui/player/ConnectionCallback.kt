package dev.marshi.jetmemo.ui.player

import android.app.Activity
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat

class ConnectionCallback(
    private val activity: Activity,
    private val mediaControllerCallback: MediaControllerCallback,
    private val mediaBrowser: () -> MediaBrowserCompat,
) : MediaBrowserCompat.ConnectionCallback() {
    private val subscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>
        ) {
            MediaControllerCompat.getMediaController(activity)?.transportControls?.prepare()
        }
    }

    override fun onConnected() {
        // https://developer.android.google.cn/guide/topics/media-apps/audio-app/building-a-mediabrowser-client?hl=ja#connect-to-mediabrowserservice
        val mediaBrowser = mediaBrowser()
        val mediaController = MediaControllerCompat(activity, mediaBrowser.sessionToken).apply {
            registerCallback(mediaControllerCallback)
        }
        MediaControllerCompat.setMediaController(
            activity,
            mediaController
        )
        mediaBrowser.subscribe(mediaBrowser.root, subscriptionCallback)
    }
}