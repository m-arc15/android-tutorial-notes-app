package com.artishevsky.notes.feature.note.domain.use_case

import app.cash.turbine.test
import com.artishevsky.notes.feature.note.domain.repository.NoteRepositoryFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class GetNotesUseCaseTest {

    private val repo = NoteRepositoryFake()
    private val getNotes = GetNotesUseCase(repo.mock)

    @Test
    fun testGettingNotesEmitsItem() = runTest {
        repo.givenNotesSuccess()

        getNotes().test {
            repo.assertGetNotesSuccess(this)
        }
    }

    @Test
    fun testGettingNotesEmitsError() = runTest {
        repo.givenNotesError()

        getNotes().test {
            repo.assertGetNotesError(this)
        }
    }

    @Test
    fun testGettingNotesEmitsNoEvents() = runTest {
        repo.givenNotesNoEvents()

        getNotes().test {
            repo.assertGetNotesNoEvents(this)
        }
    }
}
