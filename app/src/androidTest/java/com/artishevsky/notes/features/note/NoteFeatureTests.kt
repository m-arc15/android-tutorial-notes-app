package com.artishevsky.notes.features.note

import android.util.Log
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.artishevsky.notes.core.ui.MainActivity
import com.artishevsky.notes.feature.note.data.data_source.NoteDatabase
import com.artishevsky.notes.feature.note.domain.repository.NoteRepository
import com.artishevsky.notes.features.note.domain.util.DomainFixtures
import com.artishevsky.notes.features.note.presentation.notes.NotesScreenRobot
import com.artishevsky.notes.features.note.presentation.notes.notesScreenRobot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/*
Feature: Capture and organize notes
    As a user who needs to capture what's on his mind
    I want to jot down quick thoughts
    So that I can find them later when needed

    Acceptance Criteria
    - [x] I will see my available notes saved in local database
    - [ ] I can quickly capture a new note with title, description and pre-defined color
    - [ ] I can remove selected note and undo this operation
    - [ ] I can edit saved note
    - [ ] I can quickly filter notes by title, date or color
    - [ ] I can see 'No notes found' empty screen when no available notes
    - [ ] I will be prompted if I try to leave note editing without save

    Scenario 1: testLaunchingAppStartsWithNotesScreen
        Given the app is installed
        When I launch the Notes app
        Then the notes screen is displayed on home screen

    Scenario 2: testSavedNotesAreDisplayed
        Given my notes are stored in local database
        When I launch the Notes app
        Then I will see my notes saved in local database

 */

/**
 * Large End-to-End tests for the notes feature.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class NotesFeatureTests {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var repository: NoteRepository

    @Inject
    lateinit var database: NoteDatabase

    @Before
    fun setUp() {
        currentTestStep = 0

        hiltRule.inject()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testLaunchingAppStartsWithNotesScreen() {
        step("Given the app is installed")
        /* no-op required */

        step("When I launch the Notes app")
        /* no-op required */

        step("Then the notes screen is displayed on home screen")
        onNotesScreen { assertScreenTitleIsDisplayed() }
    }

    @Test
    fun testSavedNotesAreDisplayed() = runTest {
        step("Given my notes are stored in local database")
        val notes = DomainFixtures.getNotes(5)
        repository.insertNotes(notes)

        step("When I launch the Notes app")
        /* no-op required */

        step("Then I will see my notes saved in local database")
        onNotesScreen { assertNotesAreDisplayed(notes) }
    }

    private fun onNotesScreen(func: NotesScreenRobot<MainActivity>.() -> Unit) {
        notesScreenRobot(composeTestRule, func)
    }

}

// region Test Steps Logging
private const val TAG = "NotesFeatureTests"
private var currentTestStep: Int = 0
private val logTestStep: () -> String = { "[STEP %02d]".format(currentTestStep++) }
private val step: (String) -> Unit = { Log.d(TAG, "${logTestStep()} $it") }
// endregion
