package com.note.it

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.note.it.ui.root.App
import com.note.it.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "CMP-NoteIt",
        ) {
            App()
        }
    }
}