package com.henriquevieira.notes.features.main.ui

import com.henriquevieira.notes.data.model.Note


sealed class MainEvents {
    object OnPrimaryColorSelected : MainEvents()
    object OnRedColorSelected : MainEvents()
    object OnGreenColorSelected : MainEvents()
    object OnYellowColorSelected : MainEvents()
    object OnBlueColorSelected : MainEvents()
    data class OnClickSaveButton(val note: Note) : MainEvents()
    data class OnNoteSelected(val noteId: Int) : MainEvents()
}