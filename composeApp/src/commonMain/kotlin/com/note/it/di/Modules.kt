package com.note.it.di

import com.note.it.app.network.AuthDataSource
import com.note.it.app.repository.AuthRepository
import com.note.it.app.repository.AuthRepositoryImpl
import com.note.it.app.ViewModels.AuthViewModel
import com.note.it.app.ViewModels.HomeViewModel
import com.note.it.app.ViewModels.ProfileViewModel
import com.note.it.app.network.NoteDataSource
import com.note.it.app.repository.NoteRepositoryImpl
import com.note.it.app.repository.NotesRepository
import org.koin.core.module.Module
import org.koin.dsl.module


// Declared in platform module (Android, Desktop, iOS)
expect val platformModule: Module

val sharedModule = module {

    // Data Source (requires HttpClient)
    single { AuthDataSource(get()) }
    single { NoteDataSource(get()) }

    // Repository
    single<NotesRepository> { NoteRepositoryImpl(get()) }

    // ViewModel
    factory { AuthViewModel(get()) }
    factory { HomeViewModel(get()) }
    factory { ProfileViewModel(get()) }
}
