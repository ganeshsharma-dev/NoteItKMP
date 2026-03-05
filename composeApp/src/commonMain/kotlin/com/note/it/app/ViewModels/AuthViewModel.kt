package com.note.it.app.ViewModels

import androidx.lifecycle.ViewModel
import com.note.it.app.network.LoginRequest
import com.note.it.app.network.LoginResponse
import com.note.it.app.network.SignupRequest
import com.note.it.app.network.SignupResponse
import com.note.it.app.repository.AuthRepository
import com.note.it.core.domain.Result
import com.note.it.core.presentation.UiText
import com.note.it.core.presentation.toUiText
import com.note.it.ui.auth.SignupResult
import com.note.it.ui.auth.firstErrorMessage
import com.note.it.ui.utils.ValidationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {


    private val scope = CoroutineScope(Dispatchers.Default)
    fun login(
        email: String,
        password: String,
        onSuccess: (LoginResponse) -> Unit,
        onError: (UiText?) -> Unit
    ) {

        // Trim to avoid accidental spaces
        val emailTrimmed = email.trim()
        val passwordTrimmed = password.trim()

        // Validate Email/Phone
        if (emailTrimmed.isEmpty()) {
            onError(UiText.DynamicString("Email or Phone cannot be empty"))
            return
        }

        // Validate phone number pattern (optional)
        val phoneRegex = Regex("^[0-9]{10}$")
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@(.+)$")

        if (!emailRegex.matches(emailTrimmed) && !phoneRegex.matches(emailTrimmed)) {
            onError(UiText.DynamicString("Enter a valid Email or 10-digit Phone number"))
            return
        }

        // Validate password
        if (passwordTrimmed.isEmpty()) {
            onError(UiText.DynamicString("Password cannot be empty"))
            return
        }

        if (passwordTrimmed.length < 6) {
            onError(UiText.DynamicString("Password must be at least 6 characters"))
            return
        }


        // Now call repository
        scope.launch(Dispatchers.IO) {

            repository.login(LoginRequest(emailTrimmed, passwordTrimmed)).collect { result ->

                when (result) {
                    is Result.Success -> withContext(Dispatchers.Main) {
                        onSuccess(result.data)
                    }

                    is Result.Error -> withContext(Dispatchers.Main) {
                        onError(result.error.toUiText())
                    }
                }
            }
        }


        // If all validations passed
        //   onSuccess()
    }


    fun signUp(
        sr: SignupRequest,
        onSuccess: (SignupResponse) -> Unit,
        onError: (UiText?) -> Unit,
        validationError: (UiText?) -> Unit
    ) {
        val validation = isValidSignup(sr)
        if (!validation.isValid) {
            validationError(UiText.DynamicString(validation.firstErrorMessage()))
            return
        }

        scope.launch {
            repository.signup(sr).collect { result ->
                when (result) {
                    is Result.Success -> withContext(Dispatchers.Main) {
                        onSuccess(result.data)
                    }

                    is Result.Error -> withContext(Dispatchers.Main) {
                        onError(result.error.toUiText())
                    }
                }
            }
        }
    }


    fun isValidSignup(sr: SignupRequest): SignupResult {
        val name = sr.name.trim()
        val mobile = sr.mobile.trim()
        val email = sr.email.trim()
        val password = sr.password
        val confirmPwd = sr.confirmPwd
        if (name.isBlank()) {
            return SignupResult(nameError = "Name cannot be empty")
        }
        if (!ValidationUtils.isValidMobile(mobile)) {
            return SignupResult(mobileError = "Enter valid 10-digit mobile number")
        }
        if (!ValidationUtils.isValidEmail(email)) {
            return SignupResult(emailError = "Enter a valid email")
        }
        if (password.length < 6) {
            return SignupResult(passwordError = "Password must be at least 6 characters")
        }
        if (password != confirmPwd) {
            return SignupResult(confirmPwdError = "Passwords do not match")
        }

        return SignupResult(isValid = true)
    }


}