package com.note.it.platform

import android.app.Activity

actual fun exitApp() {
    val context = appContext // provide global context safely
    if (context is Activity) {
        context.finish()
    }
}