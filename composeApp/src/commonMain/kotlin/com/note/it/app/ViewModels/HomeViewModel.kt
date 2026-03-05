package com.note.it.app.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.note.it.app.network.Note
import com.note.it.app.network.NotesRequest
import com.note.it.app.network.ShareNoteRequest
import com.note.it.app.network.SharedNoteData
import com.note.it.app.repository.NotesRepository
import com.note.it.core.domain.Result
import com.note.it.core.presentation.UiText
import com.note.it.core.presentation.asPlainString
import com.note.it.core.presentation.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NotesRepository) : ViewModel() {

    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> get() = _notes

    private val _sharedNotes = mutableStateListOf<SharedNoteData>()
    val sharedNotes: List<SharedNoteData> get() = _sharedNotes





    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading




    fun fetchSharedNotes(userId: String, onError: (UiText?) -> Unit) {
        viewModelScope.launch {
            repository.getSharedNoteList(userId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _sharedNotes.clear()
                        _sharedNotes.addAll(result.data.data!!)
                    }

                    is Result.Error -> {
                        onError(result.error.toUiText())
                    }
                }
            }
        }
    }

    fun fetchNotes(userId: String, onError: (UiText?) -> Unit) {
        viewModelScope.launch {

            repository.getNoteList(userId).collect { result ->

                when (result) {

                    is Result.Success -> {
                        _notes.clear()
                        _notes.addAll(result.data.data)
                    }

                    is Result.Error -> {
                        onError(result.error.toUiText())
                    }
                }
            }
        }
    }


    fun addNote(request: NotesRequest, onError: (String) -> Unit) {

        viewModelScope.launch {
            repository.addNote(request).collect { result ->

                when (result) {

                    is Result.Success -> {
                        _notes.add(0, result.data.data)
                    }

                    is Result.Error -> {
                        onError(result.error.toUiText().asPlainString())
                    }
                }
            }
        }
    }

    fun deleteNote(userId: String, noteId: String, onError: (String) -> Unit) {

        viewModelScope.launch {
            repository.deleteNote(userId, noteId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _notes.removeAll { it.id == noteId }
                    }

                    is Result.Error -> {
                        onError(result.error.toUiText().asPlainString())
                    }
                }
            }
        }
    }

    fun updateNote(
        noteId: String,
        request: NotesRequest,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {

            repository.updateNote(noteId, request).collect { result ->

                when (result) {
                    is Result.Success -> {
                        val index = _notes.indexOfFirst { it.id == noteId }
                        if (index != -1) {
                            _notes[index] = result.data.data
                        }
                    }

                    is Result.Error -> {
                        onError(result.error.toUiText().asPlainString())
                    }
                }
            }
        }
    }

    /* fun toggleFavorite(noteId: String) {
         val index = _notes.indexOfFirst { it.id == noteId }
         if (index != -1) {
             val note = _notes[index]
             _notes[index] = note.copy(isFavorite = !note.isFavorite)
         }
     }*/

    fun toggleFavorite(
        userId: String,
        noteId: String,
        onError: (String) -> Unit
    ) {
        val index = _notes.indexOfFirst { it.id == noteId }
        if (index == -1) return

        val oldNote = _notes[index]
        val updatedNote = oldNote.copy(isFavorite = !oldNote.isFavorite)

        // 1️⃣ Update UI instantly
        _notes[index] = updatedNote

        viewModelScope.launch {

            repository.toggleFavorite(userId, noteId).collect { result ->

                when (result) {

                    is Result.Success -> {
                        // Optionally update with server response
                        _notes[index] = result.data.data
                    }

                    is Result.Error -> {
                        // 2️⃣ Revert UI if failed
                        _notes[index] = oldNote
                        onError(result.error.toUiText().asPlainString())
                    }
                }
            }
        }
    }


    fun shareNoteToUser(request: ShareNoteRequest, onSuccess:(String) -> Unit, onError: (String) -> Unit) {

        viewModelScope.launch {
            repository.shareNote(request).collect { result ->

                when (result) {

                    is Result.Success -> {
                        _sharedNotes.add(0, result.data.data)
                        onSuccess(result.data.message!!)
                    }

                    is Result.Error -> {
                        onError(result.error.toUiText().asPlainString())
                    }
                }
            }
        }
    }



    fun revokeShare(
        shareId: String,
        ownerId: String,
        onError: (String?) -> Unit = {}
    ) {
        viewModelScope.launch {
            repository.revokeShare(shareId, ownerId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        // remove from local list
                        _sharedNotes.removeAll { it.shareId == shareId }
                    }

                    is Result.Error -> {
                        onError(result.error.toUiText().asPlainString())
                    }
                }
            }
        }
    }

    fun recentContacts(): List<String> {
        return sharedNotes
            .mapNotNull { it.sharedBy?.mobile ?: it.sharedTo?.mobile }
            .filter { it.isNotBlank() }
            .distinct()
    }


    fun getFavoriteNotes(): List<Note> {
        return _notes.filter { it.isFavorite }
    }




}