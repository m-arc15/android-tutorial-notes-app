package com.artishevsky.notes.features.note.data.repository

import com.artishevsky.notes.feature.note.domain.model.Note
import com.artishevsky.notes.feature.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NoteRepositoryFake : NoteRepository {

    private val _notesFlow = MutableStateFlow<List<Note>>(listOf())
    private val notesFlow: StateFlow<List<Note>> = _notesFlow

    suspend fun emit(notes: List<Note>) {
        _notesFlow.emit(notes)
    }

    override fun getNotes(): Flow<List<Note>> {
        return notesFlow
    }
}
