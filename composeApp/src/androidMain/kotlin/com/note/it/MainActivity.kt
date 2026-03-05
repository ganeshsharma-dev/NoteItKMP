package com.note.it

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.note.it.platform.initShareContext
import com.note.it.ui.root.MainAppRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initShareContext(this)
        setContent {
            MainAppRoot()
        }

    }
}