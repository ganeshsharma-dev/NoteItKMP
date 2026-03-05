package com.note.it.core.domain


sealed interface DataError : Error {

    sealed interface Remote : DataError {

        data object REQUEST_TIMEOUT : Remote
        data object TOO_MANY_REQUESTS : Remote
        data object NO_INTERNET : Remote
        data object SERVER : Remote
        data object SERIALIZATION : Remote

        // 🔥 Dynamic message from backend
        data class BadRequest(val message: String, val field: String? = null) : Remote

        // 🔥 Unknown exception but with message
        data class ExceptionError(val reason: String) : Remote

        data object UNKNOWN : Remote
    }

    sealed interface Local : DataError {
        data object DISK_FULL : Local
        data object UNKNOWN : Local
    }

}
