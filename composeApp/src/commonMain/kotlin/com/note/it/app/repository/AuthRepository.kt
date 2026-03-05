package com.note.it.app.repository

import com.note.it.app.network.ChangePasswordRequest
import com.note.it.app.network.ChangePasswordResponse
import com.note.it.app.network.DeleteAccountRequest
import com.note.it.app.network.DeleteAccountResponse
import com.note.it.app.network.HelpRequest
import com.note.it.app.network.HelpResponse
import com.note.it.app.network.LoginRequest
import com.note.it.app.network.LoginResponse
import com.note.it.app.network.SignupRequest
import com.note.it.app.network.SignupResponse
import com.note.it.app.network.UpdateProfileRequest
import com.note.it.app.network.UpdateProfileResponse
import com.note.it.core.domain.DataError
import com.note.it.core.domain.Result

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest): Flow<Result<LoginResponse, DataError.Remote>>
    suspend fun signup(signupRequest: SignupRequest): Flow<Result<SignupResponse, DataError.Remote>>

    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Flow<Result<ChangePasswordResponse, DataError.Remote>>

    suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest): Flow<Result<UpdateProfileResponse, DataError.Remote>>

    suspend fun deleteAccount(deleteAccountRequest: DeleteAccountRequest):
            Flow<Result<DeleteAccountResponse, DataError.Remote>>

    suspend fun sendHelp(helpRequest: HelpRequest):
            Flow<Result<HelpResponse, DataError.Remote>>

    suspend fun logout()
}




