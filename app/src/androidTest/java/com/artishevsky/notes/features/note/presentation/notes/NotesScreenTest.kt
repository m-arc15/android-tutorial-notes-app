package com.artishevsky.notes.features.note.presentation.notes

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.artishevsky.notes.core.ui.MainActivity
import com.artishevsky.notes.feature.note.domain.repository.NoteRepository
import com.artishevsky.notes.feature.note.presentation.NotesScreen
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Medium Integration tests for the notes screen.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class NotesScreenTest {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    val repository: NoteRepository = mockk()

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

    private fun launchNotesScreen() {
        composeTestRule.setContent {
            NotesScreen()
        }
    }
}
