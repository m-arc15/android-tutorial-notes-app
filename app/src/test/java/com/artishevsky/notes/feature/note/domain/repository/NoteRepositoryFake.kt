package com.artishevsky.notes.feature.note.domain.repository

import app.cash.turbine.ReceiveTurbine
import com.artishevsky.notes.feature.note.domain.model.InvalidNoteException
import com.artishevsky.notes.feature.note.domain.model.Note
import com.artishevsky.notes.feature.note.domain.util.DomainFixtures
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class NoteRepositoryFake {

    val mock: NoteRepository = mockk()

    private var lastNotes: List<Note>? = null
    private var lastException: Throwable? = null

    fun givenNotesSuccess(
        notes: List<Note> = listOf(DomainFixtures.getNote(id = 1), DomainFixtures.getNote(id = 2))
    ) {
        lastNotes = notes

        coEvery { mock.getNotes() } returns flowOf(notes)
    }

    fun givenNotesError(
        exception: Throwable = InvalidNoteException("test error")
    ) {
        lastException = exception

        coEvery { mock.getNotes() } returns flow { throw exception }
    }

    fun givenNotesNoEvents() {
        coEvery { mock.getNotes() } returns flowOf()
    }

    suspend fun assertGetNotesSuccess(turbine: ReceiveTurbine<List<Note>>) {
        turbine.run {
            assertThat(awaitItem()).isEqualTo(lastNotes)
            expectNoEvents()
        }
        coVerify(exactly = 1) { mock.getNotes() }
    }

    suspend fun assertGetNotesError(turbine: ReceiveTurbine<List<Note>>) {
        turbine.run {
            assertThat(awaitError().toString()).isEqualTo(lastException.toString())
            expectNoEvents()
        }
        coVerify(exactly = 1) { mock.getNotes() }
    }

    fun assertGetNotesNoEvents(turbine: ReceiveTurbine<List<Note>>) {
        turbine.expectNoEvents()
        coVerify(exactly = 1) { mock.getNotes() }
    }
}
