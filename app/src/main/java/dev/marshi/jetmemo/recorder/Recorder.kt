package dev.marshi.jetmemo.recorder

import android.content.Context
import android.media.MediaRecorder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Recorder @Inject constructor(
    @ApplicationContext private val context: Context
) {

    var recorder: MediaRecorder? = null

    fun start(fileName: String) {
        val filePath = "${context.externalCacheDir?.absolutePath}/record_1.3gp"

        val recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(filePath)
            prepare()
        }.also {
            this.recorder = it
        }
        recorder.start()
    }

    fun stop() {
        recorder?.stop()
        recorder?.release()
        recorder = null
    }
}