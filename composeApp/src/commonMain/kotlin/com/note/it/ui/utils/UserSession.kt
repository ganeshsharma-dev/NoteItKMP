package com.note.it.ui.utils

import com.note.it.app.network.LoginData
import com.russhwolf.settings.Settings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class UserSession(private val settings: Settings) {

    private val json = Json { ignoreUnknownKeys = true }

    fun saveUser(user: LoginData?) {
        settings.putString("user_data", json.encodeToString(user))
    }

    fun getUser(): LoginData? {
        val data = settings.getStringOrNull("user_data")
        return data?.let { json.decodeFromString<LoginData>(it) }
    }

    fun clearSession() {
        settings.remove("user_data")
    }
}