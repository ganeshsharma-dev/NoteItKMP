package com.note.it.platform


import android.content.Intent
import android.content.Context
import com.note.it.app.network.Note

lateinit var appContext: Context

fun initShareContext(context: Context) {
    appContext = context
}

actual fun shareNote(note: Note) {

    val shareText = """
        ${note.title}
        
        ${note.content}
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
        setPackage("com.whatsapp") // 🔥 Force WhatsApp
    }

    try {
        appContext.startActivity(intent)
    } catch (e: Exception) {
        // WhatsApp not installed → open generic share
        val genericIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        appContext.startActivity(
            Intent.createChooser(genericIntent, "Share Note")
        )
    }
}
