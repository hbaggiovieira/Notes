package com.henriquevieira.notes.features.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.domain.NoteUseCase
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
    private val noteUseCase: NoteUseCase,
) : ViewModel() {

    private val _screen = MutableSharedFlow<MainScreenStates>()
    val screen: SharedFlow<MainScreenStates> = _screen

    private val _uiState = MutableStateFlow(MainViewState())
    val uiState = _uiState.asStateFlow()

    fun dispatch(event: MainEvents) = viewModelScope.launch {
        when (event) {
            is MainEvents.PrimaryColorSelected -> {
                changeColorState(NoteTypes.Primary)
            }
            is MainEvents.RedColorSelected -> {
                changeColorState(NoteTypes.Red)
            }
            is MainEvents.GreenColorSelected -> {
                changeColorState(NoteTypes.Green)
            }
            is MainEvents.YellowColorSelected -> {
                changeColorState(NoteTypes.Yellow)
            }
            is MainEvents.BlueColorSelected -> {
                changeColorState(NoteTypes.Blue)
            }
            is MainEvents.ClickSaveButton -> {
                onClickSaveButton(event.note)
            }
            is MainEvents.LoadSelectedNote -> {
                loadSelectedNote(event.noteId)
            }
        }
    }

    private fun onClickSaveButton(note: Note) = viewModelScope.launch {
        try {
            _uiState.value = _uiState.value.copy(
                note = note
            )
            noteUseCase.saveNote(note)
            _screen.emit(MainScreenStates.OnSaveSuccess)
        } catch (e: Exception) {
            e.printStackTrace()
            _screen.emit(MainScreenStates.OnSaveError)
        }
    }

    private fun loadSelectedNote(noteId: Int) = viewModelScope.launch {
        try {
            noteUseCase.getNoteById(noteId).collect {
                _uiState.value = _uiState.value.copy(
                    note = it
                )
            }

            _screen.emit(MainScreenStates.OnLoadNoteSuccess)
        } catch (e: Exception) {
            _screen.emit(MainScreenStates.OnLoadNoteError)
        }
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
}