package dev.marshi.jetmemo.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

fun Context.extractActivity(): Activity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) {
            return ctx
        }
        ctx = ctx.baseContext
    }
    return null
}