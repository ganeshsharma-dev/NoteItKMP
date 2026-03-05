package com.note.it.app.repository

import com.note.it.app.network.BaseResponse
import com.note.it.app.network.NotesRequest
import com.note.it.app.network.NotesListResponse
import com.note.it.app.network.NotesResponse
import com.note.it.app.network.ShareNoteRequest
import com.note.it.app.network.ShareNoteResponse
import com.note.it.app.network.SharedNoteListResponse
import com.note.it.core.domain.DataError
import com.note.it.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun getNoteList(userId: String): Flow<Result<NotesListResponse, DataError.Remote>>

    suspend fun addNote(request: NotesRequest)
            : Flow<Result<NotesResponse, DataError.Remote>>

    suspend fun updateNote(noteId: String, request: NotesRequest)
            : Flow<Result<NotesResponse, DataError.Remote>>

    suspend fun deleteNote(userId: String, noteId: String)
            : Flow<Result<Unit, DataError.Remote>>

    suspend fun toggleFavorite(userId: String, noteId: String)
            : Flow<Result<NotesResponse, DataError.Remote>>

    suspend fun getSharedNoteList(userId: String): Flow<Result<SharedNoteListResponse, DataError.Remote>>

    suspend fun shareNote(request: ShareNoteRequest)
            : Flow<Result<ShareNoteResponse, DataError.Remote>>

    suspend fun revokeShare(shareId: String, ownerId: String)
            : Flow<Result<BaseResponse, DataError.Remote>>


}