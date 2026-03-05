package com.note.it.ui.home.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(
    onBack: () -> Unit,
    onSubmitSuggestion: (String) -> Unit = {}
) {

    var suggestionText by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Suggestions") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // FAQ Section
            Text(
                text = "Frequently Asked Questions",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "• How to create a note?\n" +
                        "Tap the '+' button on the Notes tab.\n\n" +
                        "• How to edit a note?\n" +
                        "Tap on a note and choose Edit.\n\n" +
                        "• How to share a note?\n" +
                        "Open the note and select Share.",
                style = MaterialTheme.typography.bodyMedium
            )

            Divider()

            // Suggestion Section
            Text(
                text = "Send Us a Suggestion",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = suggestionText,
                onValueChange = {
                    suggestionText = it
                    errorText = null
                },
                label = { Text("Write your suggestion here...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5,
                isError = errorText != null
            )

            errorText?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    if (suggestionText.isBlank()) {
                        errorText = "Suggestion cannot be empty"
                    } else {
                        isLoading = true
                        onSubmitSuggestion(suggestionText)
                        suggestionText = ""
                        isLoading = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}