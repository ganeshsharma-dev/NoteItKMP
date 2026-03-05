package com.note.it.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.note.it.app.network.LoginData
import com.note.it.platform.exitApp
import com.note.it.platform.provideSettings
import com.note.it.ui.auth.AuthScreen
import com.note.it.ui.home.HomeMainScreen
import com.note.it.ui.utils.CommonDialog
import com.note.it.ui.utils.UserSession


sealed class RootScreen {
    object Auth : RootScreen()
    object Home : RootScreen()
}

@Composable
fun MainAppRoot() {

    val session = remember { UserSession(provideSettings()) }

    var showLogoutDialog by remember { mutableStateOf(false) }


    val initialScreen =
        if (session.getUser() != null)
            RootScreen.Home
        else
            RootScreen.Auth

    var rootScreen by remember { mutableStateOf(initialScreen) }
    var isUnlocked by remember { mutableStateOf(false) }
    var isAuthEnabled by remember { mutableStateOf(true) } // simulate toggle


    var errorDialogMessage by remember { mutableStateOf<String?>(null) }


    when {
        rootScreen is RootScreen.Auth -> {
            AuthScreen(
                onLoginSuccess = {
                    session.saveUser(it)
                    rootScreen = RootScreen.Home
                    isUnlocked = false
                }
            )
        }

        rootScreen is RootScreen.Home &&
                isAuthEnabled && !isUnlocked -> {

            AppLockScreen(
                onAuthSuccess = {
                    isUnlocked = true
                }
            )
        }

        rootScreen is RootScreen.Home -> {
            HomeMainScreen(
                onLogout = {
                    showLogoutDialog = true
                    isUnlocked = false
                }
            )
        }
    }


    errorDialogMessage?.let { msg ->
        CommonDialog(
            title = "Error",
            message = msg,
            confirmText = "OK",
            onConfirm = {
                errorDialogMessage = null
            }
        )
    }


    if (showLogoutDialog) {
        CommonDialog(
            title = "User Logout",
            message = "Are you sure you want to Logout from the app?",
            confirmText = "Yes",
            dismissText = "No",
            onConfirm = {
                showLogoutDialog = false
                session.clearSession()
                rootScreen = RootScreen.Auth

            },
            onDismiss = {
                showLogoutDialog = false
            }
        )
    }
}