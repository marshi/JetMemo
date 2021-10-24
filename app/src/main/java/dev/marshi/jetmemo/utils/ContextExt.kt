package dev.marshi.jetmemo.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

fun Context.extractActivity(): Activity {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) {
            return ctx
        }
        ctx = ctx.baseContext
    }
    throw IllegalStateException(
        "Expected an activity context for creating a HiltViewModelFactory for a " +
                "NavBackStackEntry but instead found: $ctx"
    )
}