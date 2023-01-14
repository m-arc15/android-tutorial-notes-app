package com.artishevsky.notes.features.note.domain.util

import androidx.compose.ui.graphics.toArgb
import com.artishevsky.notes.feature.note.domain.model.Note

object DomainFixtures {

    internal fun getNote(
        id: Int? = 0,
        title: String = "title",
        content: String = "content",
        timestamp: Long = 0L,
        color: Int = 0
    ): Note = Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color
    )

    internal fun getNotes(
        n: Int = 3
    ): List<Note> {
        val data = mapOf(
            "Guide to app architecture" to "This guide encompasses best practices and recommended architecture for building robust, high-quality apps.",
            "Mobile app user experiences" to "A typical Android app contains multiple app components, including activities, fragments, services, content providers, and broadcast receivers. You declare most of these app components in your app manifest. The Android OS then uses this file to decide how to integrate your app into the device's overall user experience. Given that a typical Android app might contain multiple components and that users often interact with multiple apps in a short period of time, apps need to adapt to different kinds of user-driven workflows and tasks.",
            "Common architectural principles" to "If you shouldn't use app components to store application data and state, how should you design your app instead?\n\nAs Android apps grow in size, it's important to define an architecture that allows the app to scale, increases the app's robustness, and makes the app easier to test.\n\nAn app architecture defines the boundaries between parts of the app and the responsibilities each part should have. In order to meet the needs mentioned above, you should design your app architecture to follow a few specific principles.",
            "Separation of concerns" to "The most important principle to follow is separation of concerns. It's a common mistake to write all your code in an Activity or a Fragment. These UI-based classes should only contain logic that handles UI and operating system interactions. By keeping these classes as lean as possible, you can avoid many problems related to the component lifecycle, and improve the testability of these classes.",
            "Drive UI from data models" to "Another important principle is that you should drive your UI from data models, preferably persistent models. Data models represent the data of an app. They're independent from the UI elements and other components in your app. This means that they are not tied to the UI and app component lifecycle, but will still be destroyed when the OS decides to remove the app's process from memory.",
            "Single source of truth" to "When a new data type is defined in your app, you should assign a Single Source of Truth (SSOT) to it. The SSOT is the owner of that data, and only the SSOT can modify or mutate it. To achieve this, the SSOT exposes the data using an immutable type, and to modify the data, the SSOT exposes functions or receive events that other types can call.",
        )

        return (1..n).map { i ->
            Note(
                id = i,
                title = data.keys.elementAt(i % data.size),
                content = data.values.elementAt(i % data.size),
                timestamp = System.currentTimeMillis(),
                color = Note.noteColors[i % Note.noteColors.size].toArgb()
            )
        }
    }

}
