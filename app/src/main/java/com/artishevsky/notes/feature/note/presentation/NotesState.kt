package com.artishevsky.notes.feature.note.presentation

import com.artishevsky.notes.feature.note.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList()
)
