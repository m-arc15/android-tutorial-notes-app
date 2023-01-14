package com.artishevsky.notes.core.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.artishevsky.notes.features.note.presentation.notes.NotesScreenRobot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Medium Integration tests for the main activity.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNotesScreenIsDisplayedOnLaunch() {
        NotesScreenRobot(composeTestRule).assertScreenTitleIsDisplayed()
    }

}
