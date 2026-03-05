package com.note.it.app.ViewModels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.note.it.app.network.ChangePasswordRequest
import com.note.it.app.network.ChangePasswordResponse
import com.note.it.app.network.DeleteAccountRequest
import com.note.it.app.network.DeleteAccountResponse
import com.note.it.app.network.HelpRequest
import com.note.it.app.network.HelpResponse
import com.note.it.app.network.LoginRequest
import com.note.it.app.network.UpdateProfileRequest
import com.note.it.app.network.UpdateProfileResponse
import com.note.it.app.repository.AuthRepository
import com.note.it.core.domain.Result
import com.note.it.core.presentation.UiText
import com.note.it.core.presentation.toUiText
import com.note.it.ui.utils.ValidationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Default)



    fun changePassword(
        changePasswordRequest: ChangePasswordRequest,
        onSuccess:  (ChangePasswordResponse) -> Unit,
        onError: (UiText?) -> Unit,
        validationError: (UiText?) -> Unit
    ) {

        // Trim to avoid accidental spaces
        val currentPasswordTrimmed = changePasswordRequest.currentPassword.trim()
        val newPasswordTrimmed = changePasswordRequest.newPassword.trim()
        val confirmPwdTrimmed = changePasswordRequest.confirmPassword.trim()


        // Validate Email/Phone
        if (currentPasswordTrimmed.isEmpty()) {
            validationError(UiText.DynamicString("Current Password cannot be empty"))
            return
        }
        if (currentPasswordTrimmed.length < 6) {
            validationError(UiText.DynamicString("Current Password must be at least 6 characters"))
            return
        }

        if (newPasswordTrimmed.isEmpty()) {
            validationError(UiText.DynamicString("New Password cannot be empty"))
            return
        }

        if (newPasswordTrimmed.length < 6) {
            validationError(UiText.DynamicString("New Password must be at least 6 characters"))
            return
        }

        if (confirmPwdTrimmed.length < 6) {
            validationError(UiText.DynamicString("Confirm Password must be at least 6 characters"))
            return
        }

        if (newPasswordTrimmed!=confirmPwdTrimmed) {
            validationError(UiText.DynamicString("Confirm Passwords do not match"))
            return
        }


        // Now call repository
        scope.launch(Dispatchers.IO) {

            repository.changePassword(changePasswordRequest).collect { result ->

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



    fun updateProfile(
        request: UpdateProfileRequest,
        onSuccess: (UpdateProfileResponse) -> Unit,
        onError: (UiText?) -> Unit,
        validationError: (UiText?) -> Unit
    ) {

        val nameTrimmed = request.name.trim()
        val emailTrimmed = request.email.trim()
        val mobileTrimmed = request.mobile.trim()

        // Validation
        if (nameTrimmed.isEmpty()) {
            validationError(UiText.DynamicString("Name cannot be empty"))
            return
        }

        if (!ValidationUtils.isValidMobile(mobileTrimmed)) {
            validationError(UiText.DynamicString("Enter valid 10-digit mobile number"))
            return
        }

        if (!ValidationUtils.isValidEmail(emailTrimmed)) {
            validationError(UiText.DynamicString("Enter a valid email"))
            return
        }


        // API Call
        scope.launch(Dispatchers.IO) {

            repository.updateProfile(request).collect { result ->

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



    fun deleteAccount(
        request: DeleteAccountRequest,
        onSuccess: (DeleteAccountResponse) -> Unit,
        onError: (UiText?) -> Unit,
        validationError: (UiText?) -> Unit
    ) {

        val passwordTrimmed = request.password.trim()

        if (passwordTrimmed.isEmpty()) {
            validationError(UiText.DynamicString("Password cannot be empty"))
            return
        }

        if (passwordTrimmed.length < 6) {
            validationError(UiText.DynamicString("Password must be at least 6 characters"))
            return
        }

        scope.launch(Dispatchers.IO) {

            repository.deleteAccount(request).collect { result ->

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



    fun sendHelp(
        request: HelpRequest,
        onSuccess: (HelpResponse) -> Unit,
        onError: (UiText?) -> Unit,
        validationError: (UiText?) -> Unit
    ) {

        val suggestionTrimmed = request.suggestion.trim()

        if (suggestionTrimmed.isEmpty()) {
            validationError(UiText.DynamicString("Suggestion cannot be empty"))
            return
        }

        if (suggestionTrimmed.length < 5) {
            validationError(UiText.DynamicString("Suggestion is too short"))
            return
        }

        scope.launch(Dispatchers.IO) {

            repository.sendHelp(request).collect { result ->

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

}