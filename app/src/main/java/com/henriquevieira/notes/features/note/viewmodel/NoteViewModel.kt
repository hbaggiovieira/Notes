package com.henriquevieira.notes.features.note.viewmodel

import androidx.lifecycle.viewModelScope
import com.henriquevieira.commonsui.textinput.NoteType
import com.henriquevieira.notes.base.viewmodel.BaseViewModel
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.domain.NoteUseCase
import com.henriquevieira.notes.features.note.mvi.NoteAction
import com.henriquevieira.notes.features.note.mvi.NoteResult
import com.henriquevieira.notes.features.note.mvi.NoteStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel
@Inject constructor(
    private val noteUseCase: NoteUseCase,
) : BaseViewModel<NoteAction, NoteResult, NoteStates>() {


    override val initialState: NoteStates
        get() = NoteStates()

    override fun dispatch(action: NoteAction) {
        when (action) {
            is NoteAction.CloseButtonClick -> {
                onCloseButtonClick()
            }
            is NoteAction.NoteTypeClick -> {
                changeColorState(action.noteType)
            }

            is NoteAction.UpdateTitleText -> {
                updateNoteTitle(action.title)
            }

            is NoteAction.UpdateContentText -> {
                updateNoteContentText(action.text)
            }

            is NoteAction.ClickClearButton -> {
                onClickClearButton(action.note)
            }
            is NoteAction.ClickSaveButton -> {
                onClickSaveButton(action.note)
            }
            is NoteAction.LoadSelectedNote -> {
                loadSelectedNote(action.noteId)
            }
        }
    }

    private fun onCloseButtonClick() = viewModelScope.launch {
        emitResult(NoteResult.OnCloseClick)
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
            emitResult(NoteResult.OnSaveSuccess)
        } catch (e: Exception) {
            e.printStackTrace()
            emitResult(NoteResult.OnSaveError)
        }
    }

    private fun loadSelectedNote(noteId: Int) = viewModelScope.launch {
        try {
            noteUseCase.getNoteById(noteId).collectLatest {
                updateUiState(uiState.value.copy(
                    note = it
                ))
            }

            emitResult(NoteResult.OnLoadNoteSuccess)
        } catch (e: Exception) {
            emitResult(NoteResult.OnLoadNoteError)
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