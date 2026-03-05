package com.note.it.ui.home.notes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.note.it.app.network.Note
import com.note.it.ui.utils.NoteAction

@Composable
fun NotesScreen(
    notes: List<Note>,
    onAction: (Note, NoteAction) -> Unit
) {

    if (notes.isEmpty()) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No notes yet.\nTap + to create one!",
                style = MaterialTheme.typography.bodyLarge
            )
        }

    } else {
        LazyColumn {
            items(notes, key = { it.id }) { note ->
                NoteItem(
                    note = note,
                    onAction = { action ->
                        onAction(note, action)
                    }
                )
            }

        }
    }
}
