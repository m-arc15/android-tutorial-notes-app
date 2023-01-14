package com.artishevsky.notes.core.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.artishevsky.notes.features.note.presentation.notes.NotesScreenRobot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Medium Integration tests for the main activity.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNotesScreenIsDisplayedOnLaunch() {
        NotesScreenRobot(composeTestRule).assertScreenTitleIsDisplayed()
    }

}
