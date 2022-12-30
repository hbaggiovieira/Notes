package com.henriquevieira.notes.features.note.viewmodel

import androidx.lifecycle.viewModelScope
import com.henriquevieira.commonsui.textinput.NoteType
import com.henriquevieira.notes.base.viewmodel.BaseViewModel
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.domain.NoteUseCase
import com.henriquevieira.notes.features.note.ui.NoteActions
import com.henriquevieira.notes.features.note.ui.NoteResults
import com.henriquevieira.notes.features.note.ui.NoteStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel
@Inject constructor(
    private val noteUseCase: NoteUseCase,
) : BaseViewModel<NoteActions, NoteResults, NoteStates>() {


    override val initialState: NoteStates
        get() = NoteStates()

    override fun dispatch(action: NoteActions) {
        when (action) {
            is NoteActions.NoteTypeClick -> {
                changeColorState(action.noteType)
            }

            is NoteActions.UpdateTitleText -> {
                updateNoteTitle(action.title)
            }

            is NoteActions.UpdateContentText -> {
                updateNoteContentText(action.text)
            }

            is NoteActions.ClickClearButton -> {
                onClickClearButton(action.note)
            }
            is NoteActions.ClickSaveButton -> {
                onClickSaveButton(action.note)
            }
            is NoteActions.LoadSelectedNote -> {
                loadSelectedNote(action.noteId)
            }
        }
    }

    private fun updateNoteTitle(title: String) = viewModelScope.launch {
        val note = uiState.value.note
        updateUiState(
            uiState.value.copy(
                note = Note(
                    id = note.id,
                    title = title,
                    contentText = note.contentText,
                    noteType = note.noteType
                )
            )
        )
    }

    private fun updateNoteContentText(contentText: String) = viewModelScope.launch {
        val note = uiState.value.note
        updateUiState(
            uiState.value.copy(
                note = Note(
                    id = note.id,
                    title = note.title,
                    contentText = contentText,
                    noteType = note.noteType
                )
            )
        )
    }

    private fun onClickClearButton(note: Note) = viewModelScope.launch {
        updateUiState(uiState.value.copy(
            note = note
        ))
    }

    private fun onClickSaveButton(note: Note) = viewModelScope.launch {
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

    private fun loadSelectedNote(noteId: Int) = viewModelScope.launch {
        try {
            noteUseCase.getNoteById(noteId).collectLatest {
                updateUiState(uiState.value.copy(
                    note = it
                ))
            }

            emitResult(NoteResults.OnLoadNoteSuccess)
        } catch (e: Exception) {
            emitResult(NoteResults.OnLoadNoteError)
        }
    }

    private fun changeColorState(noteType: NoteType) = viewModelScope.launch {
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
}