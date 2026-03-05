package com.note.it.di


import io.ktor.client.*
import io.ktor.client.engine.*

expect fun provideHttpClient(engine: HttpClientEngine): HttpClient
