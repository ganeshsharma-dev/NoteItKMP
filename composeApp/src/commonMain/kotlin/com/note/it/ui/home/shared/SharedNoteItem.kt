package com.note.it.ui.home.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.note.it.app.network.SharedNoteData
import com.note.it.ui.utils.DateFormatter

@Composable
fun SharedNoteItem(
    note: SharedNoteData,
    currentUserId: String,
    onClick: () -> Unit
) {

    val isSharedByMe = note.sharedType == "shared_by_me"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // 📝 Title
            Text(
                text = note.title.orEmpty(),
                style = MaterialTheme.typography.titleMedium
            )

            // 📄 Preview
            Text(
                text = note.contentPreview.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )

            Divider()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // 👤 Shared Info
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = if (isSharedByMe)
                            "Shared to ${note.sharedTo?.name.orEmpty()} By Me"
                        else
                            "Shared by ${note.sharedBy?.name.orEmpty()} To Me",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // 🕒 Shared Date
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = DateFormatter.format(note.sharedAt!!),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}