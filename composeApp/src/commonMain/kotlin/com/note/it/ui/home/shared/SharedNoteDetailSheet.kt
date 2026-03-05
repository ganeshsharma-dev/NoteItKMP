package com.note.it.ui.home.shared

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.note.it.app.network.SharedNoteData
import com.note.it.ui.utils.DateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedNoteDetailSheet(
    note: SharedNoteData,
    onDismiss: () -> Unit,
    onRevokeClick: (SharedNoteData) -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        SelectionContainer {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // 📝 Title
                Text(
                    text = note.title.orEmpty(),
                    style = MaterialTheme.typography.titleLarge
                )

                Divider()

                // 📄 Full Content
                Text(
                    text = note.contentPreview.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium
                )

                Divider()

                // 📅 Created Date
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Created: ${DateFormatter.format(note.noteCreatedAt)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // 🔁 Shared Date
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Shared: ${DateFormatter.format(note.sharedAt)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Divider()

                // 👤 Shared By
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Shared By",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = note.sharedBy?.name.orEmpty(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = note.sharedBy?.mobile.orEmpty(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Divider()

                // 👥 Shared To
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Shared To",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = note.sharedTo?.name.orEmpty(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = note.sharedTo?.mobile.orEmpty(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Divider()

                // 🔐 Permission
                Text(
                    text = "Permission: ${note.permission?.uppercase()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ❌ Revoke Button (Only if shared by me)
                if (note.sharedType == "shared_by_me") {

                    Button(
                        onClick = { onRevokeClick(note) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Revoke Access")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}