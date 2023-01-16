package com.artishevsky.notes.feature.note.domain.util

import com.artishevsky.notes.feature.note.domain.model.Note

object DomainFixtures {

    internal fun getNote(
        id: Int? = 0,
        title: String = "title",
        content: String = "content",
        timestamp: Long = 0L,
        color: Int = 0
    ): Note = Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color
    )

}
