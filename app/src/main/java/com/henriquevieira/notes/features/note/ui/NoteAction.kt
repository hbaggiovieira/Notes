package com.henriquevieira.notes.features.note.ui

import com.henriquevieira.commonsui.textinput.NoteType
import com.henriquevieira.notes.data.model.Note


sealed class NoteAction {

    object CloseButtonClick: NoteAction()
    data class NoteTypeClick(val noteType: NoteType) : NoteAction()
    data class ClickSaveButton(val note: Note) : NoteAction()
    data class ClickClearButton(val note: Note) : NoteAction()
    data class LoadSelectedNote(val noteId: Int) : NoteAction()
    data class UpdateTitleText(val title: String) : NoteAction()
    data class UpdateContentText(val text: String) : NoteAction()
}