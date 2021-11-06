package dev.marshi.jetmemo.ui.common

import android.content.Context
import android.widget.Toast

object ToastUtils {
    fun showLongToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}