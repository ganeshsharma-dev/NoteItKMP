package com.note.it

import android.app.Application
import com.note.it.di.initKoin
import org.koin.android.ext.koin.androidContext

class NoteItApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NoteItApplication)
        }

    }



}