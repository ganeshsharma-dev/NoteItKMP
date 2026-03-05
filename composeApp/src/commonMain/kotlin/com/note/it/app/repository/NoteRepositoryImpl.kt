package com.note.it.app.repository

import com.note.it.app.network.NoteDataSource
import com.note.it.app.network.NotesListResponse
import com.note.it.app.network.NotesRequest
import com.note.it.app.network.NotesResponse
import com.note.it.app.network.ShareNoteRequest
import com.note.it.app.network.SharedNoteListResponse
import com.note.it.core.domain.DataError
import com.note.it.core.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl(private val dataSource: NoteDataSource) : NotesRepository {


    override suspend fun getNoteList(userId: String): Flow<Result<NotesListResponse, DataError.Remote>> =
        flow {
            emit(dataSource.getNotes(userId))
        }

    override suspend fun addNote(request: NotesRequest) = flow {
        emit(dataSource.addNote(request))
    }

    override suspend fun updateNote(
        id: String,
        request: NotesRequest
    ) = flow {
        emit(dataSource.updateNote(id, request))
    }


    override suspend fun deleteNote(userId: String, noteId: String) = flow {
        emit(dataSource.deleteNote(userId, noteId))
    }

    override suspend fun toggleFavorite(userId: String, noteId: String) = flow {
        emit(dataSource.toggleFavorite(userId, noteId))
    }

    override suspend fun getSharedNoteList(userId: String): Flow<Result<SharedNoteListResponse, DataError.Remote>> =
        flow {
            emit(dataSource.getSharedNotes(userId))
        }


    override suspend fun shareNote(request: ShareNoteRequest) = flow {
        emit(dataSource.shareNote(request))
    }

    override suspend fun revokeShare(shareId: String, ownerId: String) = flow {
        emit(dataSource.revokeShare(shareId, ownerId))
    }

}




