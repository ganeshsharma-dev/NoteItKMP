package com.note.it.core.domain

@kotlinx.serialization.Serializable
data class ErrorResponse(
    val status: String? = null,
    val message: String? = null,
    val field: String? = null
)