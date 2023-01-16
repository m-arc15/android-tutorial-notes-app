package com.artishevsky.notes.feature.note.domain.use_case

import javax.inject.Inject

class NoteUseCases @Inject constructor(
    val getNotes: GetNotesUseCase
)
