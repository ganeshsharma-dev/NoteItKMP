package com.note.it.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.note.it.app.network.Note
import com.note.it.ui.home.favorite.FavoriteScreen
import com.note.it.ui.utils.NoteDialogAction

@Composable
fun NoteDialog(
    mode: NoteDialogAction,
    note: Note? = null,
    onDismiss: () -> Unit,
    onSave: (Note) -> Unit = {},
    onDelete: (Note) -> Unit = {}
) {

    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }
    var titleError by remember { mutableStateOf<String?>(null) }

    val isEditable = mode == NoteDialogAction.ADD || mode == NoteDialogAction.EDIT
    val isDeleteMode = mode == NoteDialogAction.DELETE

    Dialog(onDismissRequest = onDismiss,properties = DialogProperties(usePlatformDefaultWidth = false)) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 6.dp
        )  {

            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {

                Text(
                    text = when (mode) {
                        NoteDialogAction.ADD -> "Add Note"
                        NoteDialogAction.VIEW -> "View Note"
                        NoteDialogAction.EDIT -> "Edit Note"
                        NoteDialogAction.DELETE -> "Delete Note"
                    },
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(Modifier.height(16.dp))

                if (!isDeleteMode) {

                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            if (isEditable) title = it
                            titleError = null
                        },
                        label = { Text("Title") },
                        readOnly = !isEditable,
                        isError = titleError != null,
                        supportingText = {
                            titleError?.let {
                                Text(it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = content,
                        onValueChange = { if (isEditable) content = it },
                        label = { Text("Content") },
                        readOnly = !isEditable,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }

                if (isDeleteMode) {
                    Text(
                        text = "Are you sure you want to delete this note?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }

                    Spacer(Modifier.width(8.dp))

                    when (mode) {

                        NoteDialogAction.ADD -> {
                            Button(onClick = {
                                if (title.isBlank()) {
                                    titleError = "Title required"
                                    return@Button
                                }

                                onSave(
                                    Note(
                                        id = "",
                                        title = title.trim(),
                                        content = content.trim(),
                                        timestamp = "",
                                        isFavorite = false,
                                        userId = ""
                                    )
                                )
                                onDismiss()
                            }) {
                                Text("Save")
                            }
                        }

                        NoteDialogAction.EDIT -> {
                            Button(onClick = {
                                if (title.isBlank()) {
                                    titleError = "Title required"
                                    return@Button
                                }

                                onSave(
                                    note!!.copy(
                                        title = title.trim(),
                                        content = content.trim()
                                    )
                                )
                                onDismiss()
                            }) {
                                Text("Update")
                            }
                        }

                        NoteDialogAction.DELETE -> {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                ),
                                onClick = {
                                    onDelete(note!!)
                                    onDismiss()
                                }
                            ) {
                                Text("Delete")
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}
