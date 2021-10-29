package dev.marshi.jetmemo.media.player

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import dev.marshi.jetmemo.media.MediaLibrary
import java.io.FileInputStream

class MediaSessionCompatCallback(
    private val service: MediaBrowserServiceCompat,
    private val context: Context,
    private val mediaPlayer: MediaPlayer,
    private val mediaSession: () -> MediaSessionCompat
) : MediaSessionCompat.Callback(), MediaPlayer.OnCompletionListener {

    private lateinit var stateBuilder: PlaybackStateCompat.Builder

    @PlaybackStateCompat.State
    private var mediaState: Int = PlaybackStateCompat.STATE_NONE

    override fun onPrepare() {
        mediaSession().setMetadata(MediaLibrary.getMetadata(context))
        mediaPlayer.reset()
        FileInputStream(MediaLibrary.getSourceFile(context)).use {
            mediaPlayer.setDataSource(it.fd)
        }
        mediaPlayer.prepare()
        setNewState(PlaybackStateCompat.STATE_PAUSED)
    }

    override fun onPlay() {
        mediaSession().isActive = true
        mediaPlayer.start()
        service.startService(Intent(context, MediaPlaybackService::class.java))
        setNewState(PlaybackStateCompat.STATE_PLAYING)
    }

    override fun onPause() {
        mediaPlayer.pause()
        setNewState(PlaybackStateCompat.STATE_PAUSED)
    }

    override fun onStop() {
        mediaPlayer.stop()
        mediaSession().isActive = false
        service.stopSelf()
        setNewState(PlaybackStateCompat.STATE_STOPPED)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        setNewState(PlaybackStateCompat.STATE_STOPPED)
    }

    private fun setNewState(@PlaybackStateCompat.State newState: Int) {
        mediaState = newState
        stateBuilder = PlaybackStateCompat.Builder()
        stateBuilder
            .setActions(getAvailableActions())
            .setState(newState, mediaPlayer.currentPosition.toLong(), 1.0f)
        mediaSession().setPlaybackState(stateBuilder.build())
    }

    @PlaybackStateCompat.Actions
    private fun getAvailableActions(): Long {
        var actions = (
                PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID
                        or PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH
                        or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                        or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                )
        actions = when (mediaState) {
            PlaybackStateCompat.STATE_STOPPED -> actions or (
                    PlaybackStateCompat.ACTION_PLAY
                            or PlaybackStateCompat.ACTION_PAUSE
                    )
            PlaybackStateCompat.STATE_PLAYING -> actions or (
                    PlaybackStateCompat.ACTION_STOP
                            or PlaybackStateCompat.ACTION_PAUSE
                            or PlaybackStateCompat.ACTION_SEEK_TO
                    )
            PlaybackStateCompat.STATE_PAUSED -> actions or (
                    PlaybackStateCompat.ACTION_PLAY
                            or PlaybackStateCompat.ACTION_STOP
                    )
            else -> actions or (
                    PlaybackStateCompat.ACTION_PLAY
                            or PlaybackStateCompat.ACTION_PLAY_PAUSE
                            or PlaybackStateCompat.ACTION_STOP
                            or PlaybackStateCompat.ACTION_PAUSE
                    )
        }
        return actions
    }


}