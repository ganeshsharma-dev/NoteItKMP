package com.note.it

import androidx.compose.ui.window.ComposeUIViewController
import com.note.it.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
)