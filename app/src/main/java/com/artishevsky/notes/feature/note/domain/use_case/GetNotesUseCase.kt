package com.artishevsky.notes.feature.note.domain.use_case

import com.artishevsky.notes.feature.note.domain.model.Note
import com.artishevsky.notes.feature.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getNotes()
    }
}
