package com.henriquevieira.notes.features.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.domain.NoteUseCase
import com.henriquevieira.notes.features.main.ui.NoteEvents
import com.henriquevieira.notes.features.main.ui.NoteScreenStates
import com.henriquevieira.notes.features.main.ui.NoteViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel
@Inject constructor(
    private val noteUseCase: NoteUseCase,
) : ViewModel() {

    private val _screen = MutableSharedFlow<NoteScreenStates>()
    val screen: SharedFlow<NoteScreenStates> = _screen

    private val _uiState = MutableStateFlow(NoteViewState())
    val uiState = _uiState.asStateFlow()

    fun dispatch(event: NoteEvents) = viewModelScope.launch {
        when (event) {
            is NoteEvents.PrimaryColorSelected -> {
                changeColorState(NoteTypes.Primary)
            }
            is NoteEvents.RedColorSelected -> {
                changeColorState(NoteTypes.Red)
            }
            is NoteEvents.GreenColorSelected -> {
                changeColorState(NoteTypes.Green)
            }
            is NoteEvents.YellowColorSelected -> {
                changeColorState(NoteTypes.Yellow)
            }
            is NoteEvents.BlueColorSelected -> {
                changeColorState(NoteTypes.Blue)
            }
            is NoteEvents.ClickSaveButton -> {
                onClickSaveButton(event.note)
            }
            is NoteEvents.LoadSelectedNote -> {
                loadSelectedNote(event.noteId)
            }
        }
    }

    private fun onClickSaveButton(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _uiState.value = _uiState.value.copy(
                note = note
            )
            noteUseCase.saveNote(note)
            _screen.emit(NoteScreenStates.OnSaveSuccess)
        } catch (e: Exception) {
            e.printStackTrace()
            _screen.emit(NoteScreenStates.OnSaveError)
        }
    }

    private fun loadSelectedNote(noteId: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            noteUseCase.getNoteById(noteId).collect {
                _uiState.value = _uiState.value.copy(
                    note = it
                )
            }

            _screen.emit(NoteScreenStates.OnLoadNoteSuccess)
        } catch (e: Exception) {
            _screen.emit(NoteScreenStates.OnLoadNoteError)
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