package com.note.it.ui.home.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInfoScreen(
    onBack: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Info") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "NoteIt",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Version 1.0.0",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp
            )

            Text(
                text = "About This App",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "NoteIt is a simple and powerful note-taking app built using Kotlin Multiplatform and Compose Multiplatform. " +
                        "It allows users to create, edit, share, and manage notes efficiently across platforms.\n"+
                        "You can also access your notes through our official website.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Website",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "https://www.noteit.com",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                  //  openWebsite("https://www.noteit.com")
                }
            )

            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp
            )

            Text(
                text = "Developer",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Ganesh Sharma",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Android & Multiplatform Developer",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}