package com.note.it.ui.home.shared
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.note.it.app.network.SharedNoteData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedScreen(
    sharedNotes: List<SharedNoteData> = emptyList(),
    onNoteClick: (SharedNoteData) -> Unit = {},
    userId : String
) {

        if (sharedNotes.isEmpty()) {

            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No notes shared with you yet 📭",
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

                items(sharedNotes) { note ->
                    SharedNoteItem(
                        note = note,
                        onClick = { onNoteClick(note) },
                        currentUserId = userId
                    )
                }
            }
        }

}