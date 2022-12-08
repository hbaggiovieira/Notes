package com.henriquevieira.notes.features.home.ui

import com.henriquevieira.notes.data.model.Note

sealed class HomeEvents {
    data class OnCardClick(val selectedNote: Note) : HomeEvents()
    object OnAddClick : HomeEvents()
}