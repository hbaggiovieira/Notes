package com.henriquevieira.notes.features.main.ui

import com.henriquevieira.notes.features.home.model.NoteModel

sealed class MainEvents {
    object OnPrimaryColorSelected : MainEvents()
    object OnRedColorSelected : MainEvents()
    object OnGreenColorSelected : MainEvents()
    object OnYellowColorSelected : MainEvents()
    object OnBlueColorSelected : MainEvents()
    data class OnClickSaveButton(val noteModel: NoteModel) : MainEvents()
    data class OnNoteSelected(val noteModel: NoteModel) : MainEvents()
}