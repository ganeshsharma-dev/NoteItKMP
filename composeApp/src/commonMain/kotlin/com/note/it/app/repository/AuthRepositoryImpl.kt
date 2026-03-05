package com.note.it.app.repository

import com.note.it.app.network.AuthDataSource
import com.note.it.app.network.ChangePasswordRequest
import com.note.it.app.network.ChangePasswordResponse
import com.note.it.app.network.DeleteAccountRequest
import com.note.it.app.network.HelpRequest
import com.note.it.app.network.LoginRequest
import com.note.it.app.network.LoginResponse
import com.note.it.app.network.SignupRequest
import com.note.it.app.network.SignupResponse
import com.note.it.app.network.UpdateProfileRequest
import com.note.it.app.network.UpdateProfileResponse
import com.note.it.core.domain.Result
import com.note.it.core.domain.DataError
import com.note.it.ui.home.HomeScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val dataSource: AuthDataSource
) : AuthRepository {

    override suspend fun login(
        loginRequest: LoginRequest
    ): Flow<Result<LoginResponse, DataError.Remote>> = flow {
        emit(dataSource.LOGIN(loginRequest))
    }

    override suspend fun signup(signupRequest: SignupRequest):
            Flow<Result<SignupResponse, DataError.Remote>> = flow {
        emit(dataSource.SIGN_UP(signupRequest))
    }

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest):
            Flow<Result<ChangePasswordResponse, DataError.Remote>> = flow {
        emit(dataSource.CHANGE_PWD(changePasswordRequest))

    }

    override suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest):
            Flow<Result<UpdateProfileResponse, DataError.Remote>> = flow {
        emit(dataSource.UPDATE_PROFILE(updateProfileRequest))
    }

    override suspend fun deleteAccount(
        deleteAccountRequest: DeleteAccountRequest
    ) = flow {
        emit(dataSource.DELETE_ACCOUNT(deleteAccountRequest))
    }

    override suspend fun sendHelp(
        helpRequest: HelpRequest
    ) = flow {
        emit(dataSource.SEND_HELP(helpRequest))
    }

    override suspend fun logout() {
        // clear prefs if needed
    }
}
