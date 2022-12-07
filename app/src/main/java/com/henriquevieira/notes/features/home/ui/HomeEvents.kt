package com.henriquevieira.notes.features.home.ui

import com.henriquevieira.notes.features.home.model.NoteModel

sealed class HomeEvents {
    data class OnCardClick(val selectedNote: NoteModel) : HomeEvents()
    object OnAddClick : HomeEvents()
}