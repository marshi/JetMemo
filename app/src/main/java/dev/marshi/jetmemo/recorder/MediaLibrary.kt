package dev.marshi.jetmemo.recorder

import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaMetadataRetriever
import android.os.ParcelFileDescriptor
import android.support.v4.media.MediaMetadataCompat
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

object MediaLibrary {

    fun getSourceFile(context: Context): File {
        val filePath = "${context.externalCacheDir?.absolutePath}/record_1.3gp"
        val file = File(filePath)
        return file
    }

    fun getMetadata(context: Context): MediaMetadataCompat {
        val retriever = MediaMetadataRetriever().apply {
            val sourceFile = getSourceFile(context)
            setDataSource(sourceFile.absolutePath)
        }
        val meta = retriever.use { retriever ->
            MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "id")
                .putString(
                    MediaMetadataCompat.METADATA_KEY_TITLE,
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
                )
                .build()
        }
        return meta
    }
}