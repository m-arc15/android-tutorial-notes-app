package com.artishevsky.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.artishevsky.notes.features.notes.domain.NotesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesScreen()
}
