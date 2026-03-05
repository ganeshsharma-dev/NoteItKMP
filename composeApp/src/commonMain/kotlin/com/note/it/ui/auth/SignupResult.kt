package com.note.it.ui.auth

data class SignupResult(
    val nameError: String? = null,
    val mobileError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPwdError: String? = null,
    val isValid: Boolean = false
)

fun SignupResult.firstErrorMessage(): String {
    return nameError ?: mobileError ?: emailError ?: passwordError ?: confirmPwdError ?: "Unknown error"
}
