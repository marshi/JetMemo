package dev.marshi.jetmemo.recorder

import android.media.MediaPlayer
import android.media.browse.MediaBrowser
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat

class MediaPlaybackService : MediaBrowserServiceCompat() {

    private lateinit var stateBuilder: PlaybackStateCompat.Builder
    private val mediaSession: MediaSessionCompat
        get() = MediaSessionCompat(
            baseContext,
            MediaPlaybackService::class.simpleName!!
        )
    private val mediaPlayer = MediaPlayer()
    private val mediaSessionCompatCallback: MediaSessionCompatCallback
        get() = MediaSessionCompatCallback(
            this,
            baseContext,
            mediaPlayer,
            mediaSession
        )

    override fun onCreate() {
        super.onCreate()
        // https://developer.android.google.cn/guide/topics/media-apps/working-with-a-media-session?hl=ja#init-session
        mediaSession.apply {
            stateBuilder = PlaybackStateCompat.Builder()
            stateBuilder.setActions(
                PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PLAY_PAUSE
            )
            setPlaybackState(stateBuilder.build())
            setCallback(mediaSessionCompatCallback)
            setSessionToken(sessionToken)
        }
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot("app-media-root", null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        val meta = MediaLibrary.getMetadata(baseContext)
        val list = mutableListOf(
            MediaBrowserCompat.MediaItem(
                meta.description,
                MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
            )
        )
        result.sendResult(list)
    }
}