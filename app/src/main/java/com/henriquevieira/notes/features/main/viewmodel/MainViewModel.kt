package com.henriquevieira.notes.features.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.data.room.AppDatabase
import com.henriquevieira.notes.features.main.ui.MainEvents
import com.henriquevieira.notes.features.main.ui.MainScreenStates
import com.henriquevieira.notes.features.main.ui.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val appDatabase: AppDatabase,
) : ViewModel() {

    private val _screen = MutableSharedFlow<MainScreenStates>()
    val screen: SharedFlow<MainScreenStates> = _screen

    private val _uiState = MutableStateFlow(MainViewState())
    val uiState = _uiState.asStateFlow()

    fun dispatch(event: MainEvents) = viewModelScope.launch {
        when (event) {
            is MainEvents.OnPrimaryColorSelected -> {
                changeColorState(NoteTypes.Primary)
            }
            is MainEvents.OnRedColorSelected -> {
                changeColorState(NoteTypes.Red)
            }
            is MainEvents.OnGreenColorSelected -> {
                changeColorState(NoteTypes.Green)
            }
            is MainEvents.OnYellowColorSelected -> {
                changeColorState(NoteTypes.Yellow)
            }
            is MainEvents.OnBlueColorSelected -> {
                changeColorState(NoteTypes.Blue)
            }
            is MainEvents.OnClickSaveButton -> {
                onClickSaveButton(event.note)
            }
            is MainEvents.OnNoteSelected -> {
                loadSelectedNote(event.note)
            }
        }
    }

    private fun onClickSaveButton(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        _uiState.value = _uiState.value.copy(
            note = note
        )

        try {
            appDatabase.noteDao().saveNote(note)

            _screen.emit(MainScreenStates.OnSaveSuccess)
        } catch (e: Exception) {
            e.printStackTrace()
            _screen.emit(MainScreenStates.OnSaveError)
        }
    }

    private fun loadSelectedNote(note: Note) {
        _uiState.value = _uiState.value.copy(
            note = note,
        )
    }

    private fun changeColorState(noteType: NoteTypes) = viewModelScope.launch {
        val note = _uiState.value.note

        _uiState.value = _uiState.value.copy(
            note = Note(
                id = note.id,
                title = note.title,
                noteType = noteType,
                contentText = note.contentText
            )
        )
    }

    companion object {
        private const val SELECTED_COLOR_DEFAULT = "Primary"
        private const val CONTENT_TEXT_DEFAULT = ""
    }
}