package com.henriquevieira.notes.features.note.ui

import com.henriquevieira.commonsui.textinput.NoteType
import com.henriquevieira.notes.data.model.Note


sealed class NoteActions {
    data class NoteTypeClick(val noteType: NoteType) : NoteActions()
    data class ClickSaveButton(val note: Note) : NoteActions()
    data class ClickClearButton(val note: Note) : NoteActions()
    data class LoadSelectedNote(val noteId: Int) : NoteActions()
    data class UpdateTitleText(val title: String) : NoteActions()
    data class UpdateContentText(val text: String) : NoteActions()
}