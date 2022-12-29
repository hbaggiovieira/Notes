package com.henriquevieira.notes.features.note.viewmodel

import androidx.lifecycle.viewModelScope
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.base.viewmodel.BaseViewModel
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.domain.NoteUseCase
import com.henriquevieira.notes.features.note.ui.NoteActions
import com.henriquevieira.notes.features.note.ui.NoteResults
import com.henriquevieira.notes.features.note.ui.NoteStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel
@Inject constructor(
    private val noteUseCase: NoteUseCase,
) : BaseViewModel<NoteActions, NoteResults, NoteStates>() {


    override fun dispatch(action: NoteActions) {
        when (action) {
            is NoteActions.PrimaryColorSelected -> {
                changeColorState(NoteTypes.Primary)
            }
            is NoteActions.RedColorSelected -> {
                changeColorState(NoteTypes.Red)
            }
            is NoteActions.GreenColorSelected -> {
                changeColorState(NoteTypes.Green)
            }
            is NoteActions.YellowColorSelected -> {
                changeColorState(NoteTypes.Yellow)
            }
            is NoteActions.BlueColorSelected -> {
                changeColorState(NoteTypes.Blue)
            }
            is NoteActions.ClickSaveButton -> {
                onClickSaveButton(action.note)
            }
            is NoteActions.LoadSelectedNote -> {
                loadSelectedNote(action.noteId)
            }
        }
    }

    private fun onClickSaveButton(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        try {
            updateUiState(uiState.value.copy(
                note = note
            ))
            noteUseCase.saveNote(note)
            emitResult(NoteResults.OnSaveSuccess)
        } catch (e: Exception) {
            e.printStackTrace()
            emitResult(NoteResults.OnSaveError)
        }
    }

    private fun loadSelectedNote(noteId: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            noteUseCase.getNoteById(noteId).collect {
                updateUiState(uiState.value.copy(
                    note = it
                ))
            }

            emitResult(NoteResults.OnLoadNoteSuccess)
        } catch (e: Exception) {
            emitResult(NoteResults.OnLoadNoteError)
        }
    }

    private fun changeColorState(noteType: NoteTypes) = viewModelScope.launch {
        val note = uiState.value.note

        updateUiState(uiState.value.copy(
            note = Note(
                id = note.id,
                title = note.title,
                noteType = noteType,
                contentText = note.contentText
            )
        ))
    }

    override val initialState: NoteStates
        get() = NoteStates()
}