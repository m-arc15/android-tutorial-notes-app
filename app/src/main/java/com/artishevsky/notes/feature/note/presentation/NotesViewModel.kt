package com.artishevsky.notes.feature.note.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artishevsky.notes.feature.note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    init {
        getNotes()
    }

    private var getNotesJob: Job? = null
    private fun getNotes() {
        getNotesJob?.cancel()

        getNotesJob = noteUseCases.getNotes()
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                )
            }
            .launchIn(viewModelScope)
    }

}
