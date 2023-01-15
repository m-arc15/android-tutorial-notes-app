package com.artishevsky.notes.features.note.presentation.notes

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.artishevsky.notes.core.di.RepositoryModule
import com.artishevsky.notes.core.ui.MainActivity
import com.artishevsky.notes.feature.note.domain.repository.NoteRepository
import com.artishevsky.notes.feature.note.presentation.NotesScreen
import com.artishevsky.notes.features.note.data.repository.NoteRepositoryFake
import com.artishevsky.notes.features.note.domain.util.DomainFixtures
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Medium Integration tests for the notes screen.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
@UninstallModules(RepositoryModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val fakeRepository = NoteRepositoryFake()

    @BindValue
    @JvmField
    val repository: NoteRepository = fakeRepository

    @Test
    fun testScreenTitleIsDisplayed() {
        launchNotesScreen()
        NotesScreenRobot(composeTestRule).assertScreenTitleIsDisplayed()
    }

    @Test
    fun testAddNoteButtonIsDisplayed() {
        launchNotesScreen()
        NotesScreenRobot(composeTestRule).assertAddNoteButtonIsDisplayed()
    }

    @Test
    fun testSortButtonIsDisplayed() {
        launchNotesScreen()
        NotesScreenRobot(composeTestRule).assertSortOptionsButtonIsDisplayed()
    }

    @Test
    fun testNotesListIsDisplayed() = runTest {
        fakeRepository.emit(DomainFixtures.getNotes(0))

        launchNotesScreen()

        NotesScreenRobot(composeTestRule).assertNotesListIsDisplayed()
    }

    @Test
    fun testNotesListItemsAreDisplayed() = runTest {
        val notes = DomainFixtures.getNotes(6)
        fakeRepository.emit(notes)

        launchNotesScreen()

        NotesScreenRobot(composeTestRule).assertNotesAreDisplayed(notes)
    }

    private fun launchNotesScreen() {
        composeTestRule.setContent {
            NotesScreen()
        }
    }
}
