package com.artishevsky.notes.feature.note.domain.model

import com.artishevsky.notes.core.ui.theme.*

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val noteColors = listOf(Red, Yellow, Green, Fuchsia, Aqua)
    }
}

class InvalidNoteException(message: String) : Exception(message)
