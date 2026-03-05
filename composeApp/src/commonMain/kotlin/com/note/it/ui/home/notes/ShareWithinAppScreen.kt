package com.note.it.ui.home.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.note.it.app.network.Note
import com.note.it.app.network.ShareNoteRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareWithinAppScreen(
    recentContacts: List<String>,
    onBack: () -> Unit,
    onShare: Boolean,
    note: Note,
    sendToUser: (ShareNoteRequest) -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    var input by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var showErrorDialog by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val emailRegex =
        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    val mobileRegex =
        Regex("^[0-9]{10}$")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Share within App") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                    error = null
                },
                label = { Text("Email or Mobile") },
                isError = error != null,
                modifier = Modifier.fillMaxWidth()
            )

            error?.let {
                Text(
                    it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // Recent contacts auto-suggestion
            if (recentContacts.isNotEmpty()) {
                Text("Recent Contacts")

                recentContacts
                    .filter { it.contains(input, true) }
                    .take(3)
                    .forEach { contact ->

                        TextButton(
                            onClick = { input = contact }
                        ) {
                            Text(contact)
                        }
                    }
            }

            Button(
                onClick = {

                    when {
                        input.isBlank() ->
                            error = "Field cannot be empty"

                        emailRegex.matches(input).not() &&
                                mobileRegex.matches(input).not() ->
                            error = "Enter valid email or 10-digit mobile"
                        else -> {
                            isLoading = true


                            sendToUser(ShareNoteRequest(note.id, note.userId, input))
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Send Note")
            }
        }

        if (isLoading) {

            LaunchedEffect(Unit) {

             //   val result = onShare(input)

                isLoading = false

                if (onShare) {
                    snackbarHostState.showSnackbar("Note shared successfully")
                    onBack()
                } else {
                    showErrorDialog = "User not found"
                }
            }
        }
    }

    showErrorDialog?.let {
        AlertDialog(
            onDismissRequest = { showErrorDialog = null },
            title = { Text("Error") },
            text = { Text(it) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = null }) {
                    Text("OK")
                }
            }
        )
    }
}