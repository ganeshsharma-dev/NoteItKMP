package com.note.it.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.note.it.R

@Composable
actual fun LoginLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = "App Logo",
        modifier = modifier
    )
}
