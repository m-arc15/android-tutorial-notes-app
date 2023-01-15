package com.artishevsky.notes.feature.note.data.repository

import com.artishevsky.notes.feature.note.domain.model.Note
import com.artishevsky.notes.feature.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NoteRepositoryImpl : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return flowOf(listOf())
    }
}
