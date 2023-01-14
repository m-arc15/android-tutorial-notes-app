package com.artishevsky.notes.features.note.presentation.notes

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.artishevsky.notes.core.ui.MainActivity

class NotesScreenRobot constructor(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    private val screenTitle by lazy {
        composeTestRule.onNodeWithContentDescription("Your notes")
    }

    private val addNoteButton by lazy {
        composeTestRule.onNodeWithContentDescription("Add note")
    }

    private val sortOptionsButton by lazy {
        composeTestRule.onNodeWithContentDescription("Sort")
    }

    fun assertScreenTitleIsDisplayed() {
        screenTitle
            .assertIsDisplayed()
            .assertTextEquals("Your notes")
            .assertHasNoClickAction()
    }

    fun assertAddNoteButtonIsDisplayed() {
        addNoteButton
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    fun assertSortOptionsButtonIsDisplayed() {
        sortOptionsButton
            .assertIsDisplayed()
            .assertHasClickAction()
    }
}
