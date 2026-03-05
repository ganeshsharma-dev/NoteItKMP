package com.note.it.ui.home.profile
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.note.it.platform.provideSettings
import com.note.it.ui.utils.UserSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(
    onBack: () -> Unit,
    onUpdateProfile: (name: String, email: String, mobile: String) -> Unit = { _, _, _ -> },
    initialName: String,
    initialEmail: String,
    initialMobile: String
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var mobileError by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(false) }

    val session = remember { UserSession(provideSettings()) }

    LaunchedEffect(initialName, initialEmail, initialMobile) {
        name = initialName
        email = initialEmail
        mobile = initialMobile
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Profile") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = null
                },
                label = { Text("Full Name") },
                isError = nameError != null,
                modifier = Modifier.fillMaxWidth()
            )

            nameError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                },
                label = { Text("Email") },
                isError = emailError != null,
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )

            emailError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Mobile Field
            OutlinedTextField(
                value = mobile,
                onValueChange = {
                    mobile = it
                    mobileError = null
                },
                label = { Text("Mobile Number") },
                isError = mobileError != null,
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )

            mobileError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    // Basic Validation
                    when {
                        name.isBlank() -> nameError = "Name cannot be empty"
                        email.isBlank() -> emailError = "Email cannot be empty"
                        mobile.isBlank() -> mobileError = "Mobile cannot be empty"
                        else -> {
                            isLoading = true
                            onUpdateProfile(name, email, mobile)
                            isLoading = false
                        }
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Profile")
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun KeyboardOptions(keyboardType: KeyboardType) {
    TODO("Not yet implemented")
}