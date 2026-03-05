package com.note.it.ui.auth

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.auth.LoginScreen

@Preview
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            onLoginClicked = { _, _ -> },
            onForgotPasswordClick = {},
            onNavigateToRegister = {},
            errorEmail = "Invalid email or password",
            errorPassword = "Invalid Password"
        )
    }
}
