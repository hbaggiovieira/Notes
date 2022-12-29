package com.henriquevieira.notes.features.note.ui

import com.henriquevieira.notes.data.model.Note


sealed class NoteActions {
    object PrimaryColorSelected : NoteActions()
    object RedColorSelected : NoteActions()
    object GreenColorSelected : NoteActions()
    object YellowColorSelected : NoteActions()
    object BlueColorSelected : NoteActions()
    data class ClickSaveButton(val note: Note) : NoteActions()
    data class LoadSelectedNote(val noteId: Int) : NoteActions()
}