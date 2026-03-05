package com.note.it.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.note.it.app.ViewModels.AuthViewModel
import com.note.it.app.network.LoginData
import com.note.it.core.presentation.asPlainString
import com.note.it.platform.HandleBackPress
import com.note.it.platform.exitApp
import com.note.it.ui.utils.CommonDialog
import com.note.it.ui.utils.ProgressDialog
import org.koin.compose.koinInject
import ui.auth.LoginScreen
import ui.auth.SignupScreen


sealed class AuthScreen {
    object Login : AuthScreen()
    object Register : AuthScreen()
    object ForgotPassword : AuthScreen()
}


@Composable
fun AuthScreen(onLoginSuccess: (LoginData?) -> Unit) {


    var screen by remember { mutableStateOf<AuthScreen>(AuthScreen.Login) }
    var showExitDialog by remember { mutableStateOf(false) }

    HandleBackPress(onBack = {
        if (screen != AuthScreen.Login) {
            screen = AuthScreen.Login
        } else {
            showExitDialog = true
        }
    })

    val authViewModel = koinInject<AuthViewModel>()
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var mobileError by remember { mutableStateOf<String?>(null) }
    var confirmPwdError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorDialogMessage by remember { mutableStateOf<String?>(null) }


    when (screen) {

        AuthScreen.Login -> LoginScreen(
            onForgotPasswordClick = {
                screen = AuthScreen.ForgotPassword
            },
            onLoginClicked = { email, password ->
                isLoading = true
                authViewModel.login(
                    email, password,
                    onSuccess = {
                        isLoading = false
                        emailError = null
                        passwordError = null
                        // showToast("Login Successful")
                        onLoginSuccess(it.data)
                    },
                    onError = { uiText ->
                        isLoading = false
                        emailError = null
                        passwordError = null

                        val msg = uiText?.asPlainString() ?: "Unknown Error"

                        when {
                            msg?.contains("email", true) == true ||
                                    msg?.contains("phone", true) == true ->
                                emailError = msg

                            msg?.contains("password", true) == true ->
                                passwordError = msg

                            else ->

                                errorDialogMessage = msg   // ✔ SET STATE, don't show dialog here

                        }
                    }
                )
            },
            onNavigateToRegister = { screen = AuthScreen.Register },
            errorEmail = emailError,
            errorPassword = passwordError
        )

        AuthScreen.Register -> SignupScreen(
            onSignUpClicked = { signupRequest ->
                isLoading = true
                nameError = null
                mobileError = null
                emailError = null
                passwordError = null
                confirmPwdError = null

                authViewModel.signUp(
                    signupRequest,
                    onSuccess = { response ->
                        isLoading = false
                     //   errorDialogMessage = response.message

                        onLoginSuccess(response.data)

                    },
                    onError = { uiText ->
                        isLoading = false
                        val msg = uiText?.asPlainString() ?: "Unknown error"
                        errorDialogMessage = msg
                        when {
                            msg.contains("name", true) -> nameError = msg
                            msg.contains("mobile", true) -> mobileError = msg
                            msg.contains("email", true) -> emailError = msg
                            msg.contains("password", true) -> passwordError = msg
                            msg.contains("match", true) -> confirmPwdError = msg
                        }
                    }, validationError = { uiText ->
                        val msg = uiText?.asPlainString() ?: "Unknown error"
                        when {
                            msg.contains("name", true) -> nameError = msg
                            msg.contains("mobile", true) -> mobileError = msg
                            msg.contains("email", true) -> emailError = msg
                            msg.contains("password", true) -> passwordError = msg
                            msg.contains("match", true) -> confirmPwdError = msg
                            else -> {
                                errorDialogMessage = msg
                            }

                        }

                    }
                )
            },
            onNavigateToLogin = { screen = AuthScreen.Login },

            signupResult = SignupResult(
                nameError = nameError,
                mobileError = mobileError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPwdError
            )
        )

        AuthScreen.ForgotPassword -> {
            ForgotPasswordScreen(
                onBack = { screen = AuthScreen.Login },
                onSendPassword = true
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

    ProgressDialog(show = isLoading)


    if (showExitDialog) {
        CommonDialog(
            title = "Exit App",
            message = "Are you sure you want to exit the app?",
            confirmText = "Yes",
            dismissText = "No",
            onConfirm = {
                showExitDialog = false
                exitApp()
            },
            onDismiss = {
                showExitDialog = false
            }
        )
    }

}
