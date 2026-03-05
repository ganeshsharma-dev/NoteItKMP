package com.note.it.ui.utils


object ValidationUtils {

    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@(.+)$")
    private val mobileRegex = Regex("^[0-9]{10}$")

    fun isValidEmail(email: String): Boolean {
        return emailRegex.matches(email.trim())
    }

    fun isValidMobile(mobile: String): Boolean {
        return mobileRegex.matches(mobile.trim())
    }
}




