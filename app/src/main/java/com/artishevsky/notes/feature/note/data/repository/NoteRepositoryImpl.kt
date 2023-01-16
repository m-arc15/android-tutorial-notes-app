package com.artishevsky.notes.feature.note.data.repository

import com.artishevsky.notes.feature.note.data.data_source.NoteDao
import com.artishevsky.notes.feature.note.data.model.toDomain
import com.artishevsky.notes.feature.note.data.model.toEntity
import com.artishevsky.notes.feature.note.domain.model.Note
import com.artishevsky.notes.feature.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
            .map { it.toDomain() }
    }

    override suspend fun insertNotes(notes: List<Note>) {
        dao.insertAll(*notes.toEntity().toTypedArray())
    }

}
