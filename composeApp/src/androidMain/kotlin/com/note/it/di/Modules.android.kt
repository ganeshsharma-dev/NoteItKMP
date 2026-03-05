package com.note.it.di

import com.note.it.app.network.AuthDataSource
import com.note.it.app.network.NoteDataSource
import com.note.it.app.repository.AuthRepository
import com.note.it.app.repository.AuthRepositoryImpl
import com.note.it.app.repository.NoteRepositoryImpl
import com.note.it.app.repository.NotesRepository
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.dsl.module
import org.koin.core.module.Module

// Android HttpClient
actual fun provideHttpClient(engine: HttpClientEngine): HttpClient =
    HttpClient(engine) {

        expectSuccess = false

        // JSON serialization plugin
        install(ContentNegotiation) {
            json()  // NOW WORKS
        }

        // Logging plugin (FULL LOGS TO LOGCAT)
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    android.util.Log.d("Ktor", message)
                }
            }
            level = LogLevel.ALL
        }
    }

// Android DI
actual val platformModule: Module = module {

    single<HttpClientEngine> { OkHttp.create() }

    single { provideHttpClient(get()) }

    single { AuthDataSource(get()) }
    single { NoteDataSource(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<NotesRepository> { NoteRepositoryImpl(get()) }

}
