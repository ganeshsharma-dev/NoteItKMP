package com.note.it.app.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
    @SerialName("identifier")
    val email: String,
    @SerialName("password")
    val password: String
)

@Serializable
data class LoginResponse(
    @SerialName("data") var data: LoginData? = LoginData()
) : BaseResponse()

@Serializable
data class LoginData(
    @SerialName("id") var userId: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("email") var email: String? = null,
    @SerialName("mobile") var mobile: String? = null
)

@Serializable
data class SignupRequest(
    @SerialName("name")
    val name: String,
    @SerialName("mobile")
    val mobile: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,

    val confirmPwd: String
)

@Serializable
data class SignupResponse(
    @SerialName("data") var data: LoginData? = LoginData()
) : BaseResponse()


@Serializable
data class ChangePasswordRequest(
    @SerialName("id")
    val id: String,
    @SerialName("currentPassword")
    val currentPassword: String,
    @SerialName("newPassword")
    val newPassword: String,
    @SerialName("confirmPassword")
    val confirmPassword: String,
)

@Serializable
data class ChangePasswordResponse(
    @SerialName("data")
    var data: LoginData? = null
) : BaseResponse()


@Serializable
data class UpdateProfileRequest(
    @SerialName("id")
    val id: String,
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val name: String,
    @SerialName("mobile")
    val mobile: String,
)

@Serializable
data class UpdateProfileResponse(
    @SerialName("data")
    var data: LoginData? = null
) : BaseResponse()


@Serializable
data class DeleteAccountRequest(
    @SerialName("id")
    val id: String,
    @SerialName("password")
    val password: String,
)

@Serializable
class DeleteAccountResponse : BaseResponse()


@Serializable
data class HelpRequest(
    @SerialName("userId")
    val id: String,
    @SerialName("message")
    val suggestion: String,
)

@Serializable
class HelpResponse(
    @SerialName("data")
    var data: TicketData? = null
) : BaseResponse()



@Serializable
data class TicketData(

    @SerialName("ticketId") var ticketId: String? = null,
    @SerialName("status") var status: String? = null,
    @SerialName("message") var message: String? = null

)



