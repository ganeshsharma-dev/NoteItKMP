package com.note.it.ui.home
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomeTab(val title: String, val icon: ImageVector) {
    data object Notes : HomeTab("Notes", Icons.Filled.Home)
    data object Favorite : HomeTab("Favorite", Icons.Filled.Star)
    data object Shared : HomeTab("Shared", Icons.Filled.Share)
    data object Profile : HomeTab("Profile", Icons.Filled.Person)

    companion object {
        // Use a function or a getter to ensure objects exist before the list is built
        val all get() = listOf(Notes, Favorite, Shared, Profile)
    }
}