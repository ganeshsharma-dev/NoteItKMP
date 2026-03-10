package com.note.it.ui.home.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.SecurityUpdate
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.note.it.ui.utils.ProfileAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userName: String = "Ganesh Sharma",
    userEmail: String = "ganesh@example.com",
    userMobile: String = "+91 8083200081",
    totalNotes: Int = 12,
    favoriteNotes: Int = 4,
    sharedNotes: Int = 2,
    onAction: (ProfileAction) -> Unit
) {

    Scaffold() { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Profile Image
            Surface(
                shape = CircleShape,
                tonalElevation = 4.dp,
                modifier = Modifier.size(100.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = userName.first().uppercase(),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = userName,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = userEmail,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = userMobile,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(24.dp))

            // Stats Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStatItem("Notes", totalNotes)
                ProfileStatItem("Favorites", favoriteNotes)
                ProfileStatItem("Shared", sharedNotes)
            }

            Spacer(Modifier.height(30.dp))

            Divider()

            Spacer(Modifier.height(16.dp))

            // Settings Options
            ProfileOptionItem(
                onClick = {onAction(ProfileAction.UPDATE_PROFILE)},
                icon = Icons.Default.Update,
                text = "Update Profile"
            )

            ProfileOptionItem(
                onClick = {onAction(ProfileAction.CHANGE_PASSWORD)},
                icon = Icons.Default.Password,
                text = "Change Password"
            )
            ProfileOptionItem(
                onClick = {onAction(ProfileAction.SECURITY_SETTINGS)},
                icon = Icons.Default.SecurityUpdate,
                text = "Security Setting"
            )
            ProfileOptionItem(
                onClick = {onAction(ProfileAction.DELETE_ACCOUNT)},
                icon = Icons.Default.DeleteForever,
                text = "Delete Account"
            )

            ProfileOptionItem(
                onClick = {onAction(ProfileAction.INFO)},
                icon = Icons.Default.Info,
                text = "About App"
            )

            ProfileOptionItem(
                onClick = {onAction(ProfileAction.HELP)},
                icon = Icons.Default.Help,
                text = "Help & Support"
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { onAction(ProfileAction.LOGOUT) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Logout")
            }
        }
    }
}
