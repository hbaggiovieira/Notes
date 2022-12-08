package com.henriquevieira.notes.features.main.ui

import com.henriquevieira.notes.data.model.Note


sealed class MainEvents {
    object PrimaryColorSelected : MainEvents()
    object RedColorSelected : MainEvents()
    object GreenColorSelected : MainEvents()
    object YellowColorSelected : MainEvents()
    object BlueColorSelected : MainEvents()
    data class ClickSaveButton(val note: Note) : MainEvents()
    data class LoadSelectedNote(val noteId: Int?) : MainEvents()
}