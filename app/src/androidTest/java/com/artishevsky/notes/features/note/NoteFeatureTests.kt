package com.artishevsky.notes.features.note

import android.util.Log
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.artishevsky.notes.core.ui.MainActivity
import com.artishevsky.notes.features.note.presentation.notes.NotesScreenRobot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
Feature: Capture and organize notes
    As a user who needs to capture what's on his mind
    I want to jot down quick thoughts
    So that I can find them later when needed

    Acceptance Criteria
    - [ ] I will see my available notes saved in local database
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

 */

/**
 * Large End-to-End tests for the notes feature.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class NotesFeatureTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUpTestCase() {
        currentTestStep = 0
    }

    @Test
    fun testLaunchingAppStartsWithNotesScreen() {
        step("Given the app is installed")
        /* no-op required */

        step("When I launch the Notes app")
        /* no-op required */

        step("Then the notes screen is displayed on home screen")
        NotesScreenRobot(composeTestRule).assertScreenTitleIsDisplayed()
    }

}

// region Test Steps Logging
private const val TAG = "NotesFeatureTests"
private var currentTestStep: Int = 0
private val logTestStep: () -> String = { "[STEP %02d]".format(currentTestStep++) }
private val step: (String) -> Unit = { Log.d(TAG, "${logTestStep()} $it") }
// endregion
