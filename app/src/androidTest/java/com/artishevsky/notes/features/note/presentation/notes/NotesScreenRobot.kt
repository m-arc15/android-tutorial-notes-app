package com.artishevsky.notes.features.note.presentation.notes

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.artishevsky.notes.feature.note.domain.model.Note

class NotesScreenRobot<T : ComponentActivity> constructor(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<T>, T>
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

    private val notesList by lazy {
        composeTestRule.onNode(hasTestTag("Notes list"))
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

    fun assertNotesListIsDisplayed() {
        notesList
            .assertIsDisplayed()
            .assertHasNoClickAction()
    }

    fun assertNotesAreDisplayed(notes: List<Note>) {
        notes.forEach {
            notesList.performScrollToNode(hasText(it.title))
        }
    }
}

fun <T : ComponentActivity> notesScreenRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<T>, T>,
    func: NotesScreenRobot<T>.() -> Unit
): NotesScreenRobot<T> = NotesScreenRobot(composeTestRule).apply { func() }
