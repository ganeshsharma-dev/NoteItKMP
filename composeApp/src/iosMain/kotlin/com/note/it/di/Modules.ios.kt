package com.note.it.di

import com.note.it.app.repository.AuthRepository
import com.note.it.app.repository.AuthRepositoryImpl
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single<AuthRepository> { AuthRepositoryImpl(get()) }

    }