package com.note.it.app.network

import com.note.it.core.data.safeCall
import com.note.it.core.domain.DataError
import com.note.it.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class NoteDataSource(private val client: HttpClient) {

    private val BASE_URL = "http://103.212.135.69:5000"

    suspend fun getNotes(userId : String)
            : Result<NotesListResponse, DataError.Remote> =
        safeCall<NotesListResponse> {
            client.get("${BASE_URL}/api/notes/user/"+userId) {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")

            }
        }
    suspend fun addNote(request: NotesRequest)
            : Result<NotesResponse, DataError.Remote> =
        safeCall {
            client.post("${BASE_URL}/api/notes") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")
                setBody(request)
            }
        }

    suspend fun updateNote(noteId: String, request: NotesRequest)
            : Result<NotesResponse, DataError.Remote> =
        safeCall {
            client.put("${BASE_URL}/api/notes/${request.userId}/${noteId}") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")
                setBody(request)
            }
        }

    suspend fun deleteNote(userId : String ,noteId: String)
            : Result<Unit, DataError.Remote> =
        safeCall {
            client.delete("$BASE_URL/api/notes/$userId/$noteId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")
            }
        }

    suspend fun toggleFavorite(userId: String,noteId: String)
            : Result<NotesResponse, DataError.Remote> =
        safeCall {
            client.patch("$BASE_URL/api/notes/$userId/$noteId/favorite"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")
            }
        }

    suspend fun getSharedNotes(userId: String)
            : Result<SharedNoteListResponse, DataError.Remote> =
        safeCall {
            client.get("$BASE_URL/api/notes/user/shared-notes/$userId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")
            }
        }


    suspend fun shareNote(request: ShareNoteRequest)
            : Result<ShareNoteResponse, DataError.Remote> =
        safeCall {
            client.post("${BASE_URL}/api/notes/share-note") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Accept, "application/json")
                setBody(request)
            }
        }

    suspend fun revokeShare(
        shareId: String,
        ownerId: String
    ): Result<BaseResponse, DataError.Remote> =
        safeCall {
            client.delete("$BASE_URL/api/notes/revoke-share/$shareId/$ownerId")
        }
}