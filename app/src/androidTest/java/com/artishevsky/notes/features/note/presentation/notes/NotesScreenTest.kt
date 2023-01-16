package com.artishevsky.notes.features.note.presentation.notes

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.artishevsky.notes.feature.note.domain.use_case.NoteUseCases
import com.artishevsky.notes.feature.note.presentation.NotesScreen
import com.artishevsky.notes.feature.note.presentation.NotesViewModel
import com.artishevsky.notes.features.note.domain.util.DomainFixtures
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Medium Integration tests for the notes screen with view model.
 * Testing in isolation with ComponentActivity.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@MediumTest
class NotesScreenTest {

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val mockUseCases: NoteUseCases = mockk(relaxed = true)

    @Test
    fun testScreenTitleIsDisplayed() {
        launchNotesScreen()
        onNotesScreen { assertScreenTitleIsDisplayed() }
    }

    @Test
    fun testAddNoteButtonIsDisplayed() {
        launchNotesScreen()
        onNotesScreen { assertAddNoteButtonIsDisplayed() }
    }

    @Test
    fun testSortButtonIsDisplayed() {
        launchNotesScreen()
        onNotesScreen { assertSortOptionsButtonIsDisplayed() }
    }

    @Test
    fun testNotesListIsDisplayed() = runTest {
        launchNotesScreen()

        onNotesScreen { assertNotesListIsDisplayed() }
    }

    @Test
    fun testNotesListItemsAreDisplayed() = runTest {
        val notes = DomainFixtures.getNotes(6)
        coEvery { mockUseCases.getNotes() } returns flowOf(notes)

        launchNotesScreen()

        onNotesScreen { assertNotesAreDisplayed(notes) }
    }

    private fun launchNotesScreen() {
        composeTestRule.setContent {
            NotesScreen(
                viewModel = NotesViewModel(
                    noteUseCases = mockUseCases
                )
            )
        }
    }

    private fun onNotesScreen(func: NotesScreenRobot<ComponentActivity>.() -> Unit) {
        notesScreenRobot(composeTestRule, func)
    }

}
