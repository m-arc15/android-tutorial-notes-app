package com.artishevsky.notes.feature.note.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artishevsky.notes.feature.note.domain.model.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
)

fun Note.toEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp,
    color = color
)

fun NoteEntity.toDomain() = Note(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp,
    color = color
)

fun List<NoteEntity>.toDomain() = this.map { it.toDomain() }

fun List<Note>.toEntity() = this.map { it.toEntity() }
