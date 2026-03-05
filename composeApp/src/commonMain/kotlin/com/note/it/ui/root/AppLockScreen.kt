package com.note.it.ui.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.note.it.platform.performBiometricAuth

@Composable
fun AppLockScreen(
    onAuthSuccess: () -> Unit
) {
    LaunchedEffect(Unit) {
        // Call platform biometric auth here
        val success = performBiometricAuth()

        if (success) {
            onAuthSuccess()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Authenticating...")
    }
}