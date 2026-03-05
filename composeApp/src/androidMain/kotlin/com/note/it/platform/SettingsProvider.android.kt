package com.note.it.platform

import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import android.content.Context



actual fun provideSettings(): Settings {
    return SharedPreferencesSettings(
        appContext.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    )
}