package com.artishevsky.notes.features.notes

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.artishevsky.notes.MainActivity
import com.artishevsky.notes.features.notes.domain.NotesScreen
import com.artishevsky.notes.features.notes.domain.NotesScreenRobot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Medium Integration tests for the notes screen.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
class NotesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testScreenTitleIsDisplayed() {
        launchNotesScreen()
        NotesScreenRobot(composeTestRule).assertScreenTitleIsDisplayed()
    }

    private fun launchNotesScreen() {
        composeTestRule.setContent {
            NotesScreen()
        }
    }
}
