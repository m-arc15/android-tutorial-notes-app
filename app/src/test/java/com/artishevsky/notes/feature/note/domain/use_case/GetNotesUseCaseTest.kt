package com.artishevsky.notes.feature.note.domain.use_case

import app.cash.turbine.test
import com.artishevsky.notes.feature.note.domain.model.InvalidNoteException
import com.artishevsky.notes.feature.note.domain.repository.NoteRepository
import com.artishevsky.notes.feature.note.domain.util.DomainFixtures
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetNotesUseCaseTest {

    private lateinit var getNotes: GetNotesUseCase
    private val repo: NoteRepository = mockk()

    @BeforeEach
    fun setUp() {
        getNotes = GetNotesUseCase(repo)
    }

    @Test
    fun testGettingNotesEmitsItem() = runTest {
        val notes = listOf(DomainFixtures.getNote(id = 1), DomainFixtures.getNote(id = 2))
        coEvery { repo.getNotes() } returns flowOf(notes)

        getNotes().test {
            assertThat(awaitItem()).isEqualTo(notes)
            expectNoEvents()
        }
    }

    @Test
    fun testGettingNotesEmitsError() = runTest {
        val exceptionMessage = "test error"
        coEvery { repo.getNotes() } returns flow {
            throw InvalidNoteException(exceptionMessage)
        }

        getNotes().test {
            assertThat(awaitError().message).isEqualTo(exceptionMessage)
        }
    }

    @Test
    fun testGettingNotesEmitsNoEvents() = runTest {
        coEvery { repo.getNotes() } returns flowOf()

        getNotes().test {
            expectNoEvents()
        }
    }
}
