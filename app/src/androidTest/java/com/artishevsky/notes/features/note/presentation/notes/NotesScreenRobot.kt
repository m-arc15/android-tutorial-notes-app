package com.artishevsky.notes.features.note.presentation.notes

import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.artishevsky.notes.core.ui.MainActivity

class NotesScreenRobot constructor(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    private val screenTitle by lazy {
        composeTestRule.onNodeWithContentDescription("Your notes")
    }

    fun assertScreenTitleIsDisplayed() {
        screenTitle
            .assertIsDisplayed()
            .assertTextEquals("Your notes")
            .assertHasNoClickAction()
    }
}
