package com.note.it.app.network

import com.note.it.core.data.safeCall
import com.note.it.core.domain.DataError
import com.note.it.core.domain.Result
import com.note.it.ui.home.HomeScreen
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthDataSource(private val client: HttpClient) {

    private val BASE_URL = "http://103.212.135.69:5000"

    suspend fun LOGIN(loginRequest: LoginRequest)
            : Result<LoginResponse, DataError.Remote> =
        safeCall<LoginResponse> {
            client.post("${BASE_URL}/login") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")
                setBody(loginRequest)

            }
        }

    suspend fun SIGN_UP(signupRequest: SignupRequest)
            : Result<SignupResponse, DataError.Remote> =
        safeCall<SignupResponse> {
            client.post("${BASE_URL}/register") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")
                setBody(signupRequest)
            }
        }

    suspend fun CHANGE_PWD(changePasswordRequest: ChangePasswordRequest)
            : Result<ChangePasswordResponse, DataError.Remote> =
        safeCall<ChangePasswordResponse> {
            client.post("${BASE_URL}/change-password") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")
                setBody(changePasswordRequest)

            }
        }

    suspend fun UPDATE_PROFILE(updateProfileRequest: UpdateProfileRequest)
            : Result<UpdateProfileResponse, DataError.Remote> =
        safeCall<UpdateProfileResponse> {
            client.post("${BASE_URL}/update-profile") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")
                setBody(updateProfileRequest)
            }
        }

     suspend fun DELETE_ACCOUNT(
        request: DeleteAccountRequest
    ): Result<DeleteAccountResponse, DataError.Remote> =
        safeCall<DeleteAccountResponse> {
            client.post("${BASE_URL}/delete-account") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")
                setBody(request)
            }
        }

     suspend fun SEND_HELP(
        request: HelpRequest
    ): Result<HelpResponse, DataError.Remote> =
        safeCall<HelpResponse> {
            client.post("${BASE_URL}/help-support") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")
                setBody(request)
            }
        }
}

