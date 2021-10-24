package dev.marshi.jetmemo.recorder

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream

class MediaSessionCompatCallback(
    private val service: MediaBrowserServiceCompat,
    private val context: Context,
    private val mediaPlayer: MediaPlayer,
    private val mediaSession: MediaSessionCompat
) : MediaSessionCompat.Callback() {
    override fun onPrepare() {
        mediaSession.setMetadata(MediaLibrary.getMetadata(context))
        mediaPlayer.reset()
        FileInputStream(MediaLibrary.getSourceFile(context)).use {
            mediaPlayer.setDataSource(it.fd)
        }
        mediaPlayer.prepare()
    }

    override fun onPlay() {
        mediaSession.isActive = true
        mediaPlayer.start()
        service.startService(Intent(context, MediaPlaybackService::class.java))
    }

    override fun onPause() {
        mediaPlayer.pause()
    }

    override fun onStop() {
        mediaPlayer.stop()
        mediaSession.isActive = false
        service.stopSelf()
    }
}