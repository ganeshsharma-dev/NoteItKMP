package com.note.it.di

import com.note.it.app.network.AuthDataSource
import com.note.it.app.network.NoteDataSource
import com.note.it.app.repository.AuthRepository
import com.note.it.app.repository.AuthRepositoryImpl
import com.note.it.app.repository.NoteRepositoryImpl
import com.note.it.app.repository.NotesRepository
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module



actual val platformModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single { AuthDataSource(get()) }
    single { NoteDataSource(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<NotesRepository> { NoteRepositoryImpl(get()) }

}