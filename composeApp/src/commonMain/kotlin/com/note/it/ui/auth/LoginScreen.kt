package ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.note.it.ui.auth.LoginLogo


@Composable
fun LoginScreen(
    onLoginClicked: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onForgotPasswordClick:() -> Unit,
    errorEmail: String? = null,
    errorPassword: String? = null,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    LaunchedEffect(errorEmail, errorPassword) {
        when {
            errorEmail != null -> emailFocusRequester.requestFocus()
            errorPassword != null -> passwordFocusRequester.requestFocus()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        LoginLogo(Modifier.size(220.dp).padding(bottom = 24.dp))


        //  Text("NoteIt", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
            .focusRequester(emailFocusRequester),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email or Phone Number") },
            isError = errorEmail != null,
            supportingText = {
                if (errorEmail != null) {
                    Text(
                        text = errorEmail!!,
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
            visualTransformation = PasswordVisualTransformation(),
            isError = errorPassword != null,
            supportingText = {
                if (errorPassword != null) {
                    Text(
                        text = errorPassword!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )




        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onForgotPasswordClick) {
                Text("Forgot Password?")
            }
        }



        Button(
            onClick = { onLoginClicked(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(Modifier.height(12.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text("Create an account")
        }
    }


}




