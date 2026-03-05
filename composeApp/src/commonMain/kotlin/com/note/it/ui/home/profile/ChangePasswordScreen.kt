package com.note.it.ui.home.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.note.it.ui.utils.PasswordField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    onChangePassword: (current: String, new: String,confirm: String) -> Unit,
    onBack: () -> Unit,
    errorCurrent: String? = null,
    errorNew: String? = null,
    errorConfirm: String? = null,
    isLoading: Boolean = false
) {

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val oldPwdFocus = remember { FocusRequester() }
    val newPwdFocus = remember { FocusRequester() }
    val confirmPwdFocus = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(errorCurrent, errorNew, errorConfirm) {

        val targetFocus = when {
            errorCurrent != null -> oldPwdFocus
            errorNew != null -> newPwdFocus
            errorConfirm != null -> confirmPwdFocus
            else -> null
        }

        targetFocus?.let {
            kotlinx.coroutines.delay(150)
            focusManager.clearFocus(force = true)
            it.requestFocus()
        }
    }


    var currentVisible by remember { mutableStateOf(false) }
    var newVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Change Password") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(Modifier.height(20.dp))

            // 🔒 Current Password
            PasswordField(
                modifier = Modifier.focusRequester(oldPwdFocus),
                value = currentPassword,
                onValueChange = { currentPassword = it },
                label = "Current Password",
                isVisible = currentVisible,
                onToggleVisibility = { currentVisible = !currentVisible },
                error = errorCurrent
            )

            Spacer(Modifier.height(16.dp))

            // 🔑 New Password
            PasswordField(
                modifier = Modifier.focusRequester(newPwdFocus),
                value = newPassword,
                onValueChange = { newPassword = it },
                label = "New Password",
                isVisible = newVisible,
                onToggleVisibility = { newVisible = !newVisible },
                error = errorNew
            )

            Spacer(Modifier.height(16.dp))

            // 🔁 Confirm Password
            PasswordField(
                modifier = Modifier.focusRequester(confirmPwdFocus),
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                isVisible = confirmVisible,
                onToggleVisibility = { confirmVisible = !confirmVisible },
                error = errorConfirm
            )

            Spacer(Modifier.height(30.dp))

            Button(
                onClick = {
                        onChangePassword(currentPassword, newPassword,confirmPassword)

                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Update Password")
                }
            }
        }
    }
}
