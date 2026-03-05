package com.note.it.platform

import com.note.it.app.network.Note

expect fun shareNote(note: Note)


expect suspend fun performBiometricAuth(): Boolean


expect fun exitApp()