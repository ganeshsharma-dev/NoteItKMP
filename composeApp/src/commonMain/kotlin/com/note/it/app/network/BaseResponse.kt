package com.note.it.app.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
open class BaseResponse(
    @SerialName("status") var status: String? = null,
    @SerialName("message") var message: String? = null,
)