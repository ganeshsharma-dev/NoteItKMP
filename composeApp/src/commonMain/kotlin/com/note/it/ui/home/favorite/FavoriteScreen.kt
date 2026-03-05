package com.note.it.ui.home.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.note.it.app.network.Note

@Composable
fun FavoriteScreen(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit = {},
    onToggleFavorite: (Note) -> Unit = {}
) {

    // Filter only favorite notes
    val favoriteNotes = notes.filter { it.isFavorite }

    if (favoriteNotes.isEmpty()) {

        // Empty State UI
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No favorite notes yet ❤️",
                style = MaterialTheme.typography.bodyLarge
            )
        }

    } else {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(favoriteNotes) { note ->
                FavoriteNoteItem(
                    note = note,
                    onClick = { onNoteClick(note) },
                    onToggleFavorite = { onToggleFavorite(note) }
                )
            }
        }
    }
}
