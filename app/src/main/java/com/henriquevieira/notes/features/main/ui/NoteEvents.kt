package com.henriquevieira.notes.features.main.ui

import com.henriquevieira.notes.data.model.Note


sealed class NoteEvents {
    object PrimaryColorSelected : NoteEvents()
    object RedColorSelected : NoteEvents()
    object GreenColorSelected : NoteEvents()
    object YellowColorSelected : NoteEvents()
    object BlueColorSelected : NoteEvents()
    data class ClickSaveButton(val note: Note) : NoteEvents()
    data class LoadSelectedNote(val noteId: Int) : NoteEvents()
}