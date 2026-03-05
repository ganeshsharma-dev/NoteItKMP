package ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.note.it.app.network.SignupRequest
import com.note.it.ui.auth.LoginLogo
import com.note.it.ui.auth.SignupResult

@Composable
fun SignupScreen(
    onSignUpClicked: (SignupRequest) -> Unit,
    onNavigateToLogin: () -> Unit,
    signupResult: SignupResult
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val nameFocusRequester = remember { FocusRequester() }
    val mobileFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }



    LaunchedEffect(signupResult) {
        when {
            signupResult.nameError != null -> nameFocusRequester.requestFocus()
            signupResult.emailError != null -> emailFocusRequester.requestFocus()
            signupResult.passwordError != null -> passwordFocusRequester.requestFocus()
            signupResult.confirmPwdError != null -> confirmPasswordFocusRequester.requestFocus()
            signupResult.mobileError != null -> mobileFocusRequester.requestFocus()
        }
    }


    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        //  Text("Sign Up", style = MaterialTheme.typography.headlineMedium)

        LoginLogo(Modifier.size(200.dp).padding(bottom = 24.dp))

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().focusRequester(nameFocusRequester),
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            isError = signupResult.nameError != null,
            supportingText = {
                if (signupResult.nameError != null) {
                    Text(
                        text = signupResult.nameError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().focusRequester(mobileFocusRequester),
            value = mobile,
            onValueChange = { newValue ->
                if (newValue.length <= 10 && newValue.all { it.isDigit() }) {
                    mobile = newValue
                }
            },
            label = { Text("Mobile") },
            isError = signupResult.mobileError != null,

            supportingText = {
                if (signupResult.mobileError != null) {
                    Text(
                        text = signupResult.mobileError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)


        )
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().focusRequester(emailFocusRequester),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            isError = signupResult.emailError != null,
            supportingText = {
                if (signupResult.emailError != null) {
                    Text(
                        text = signupResult.emailError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().focusRequester(passwordFocusRequester),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            // 👇 THIS LINE FIXES THE ISSUE
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),

            isError = signupResult.passwordError != null,

            supportingText = {
                if (signupResult.passwordError != null) {
                    Text(
                        text = signupResult.passwordError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle Password"
                    )
                }
            }
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().focusRequester(confirmPasswordFocusRequester),
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = if (confirmPasswordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            isError = signupResult.confirmPwdError != null,
            supportingText = {
                if (signupResult.confirmPwdError != null) {
                    Text(
                        text = signupResult.confirmPwdError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }, trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle Password"
                    )
                }
            }
        )


        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                onSignUpClicked(
                    SignupRequest(
                        name,
                        mobile,
                        email,
                        password,
                        confirmPassword
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account")
        }

        Spacer(Modifier.height(12.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text("Back to Login")
        }

    }


}