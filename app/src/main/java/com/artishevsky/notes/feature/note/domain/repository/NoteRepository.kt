package com.artishevsky.notes.feature.note.domain.repository

import com.artishevsky.notes.feature.note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun insertNotes(notes: List<Note>)
}
