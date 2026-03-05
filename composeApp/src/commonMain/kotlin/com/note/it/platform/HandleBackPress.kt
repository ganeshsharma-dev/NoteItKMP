package com.note.it.platform

import androidx.compose.runtime.Composable

@Composable
expect fun HandleBackPress(onBack: () -> Unit)