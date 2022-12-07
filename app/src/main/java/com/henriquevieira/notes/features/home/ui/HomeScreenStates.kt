package com.henriquevieira.notes.features.home.ui

import com.henriquevieira.notes.features.home.model.NoteModel

sealed class HomeScreenStates {
    data class OnCardClick(val selectedNote: NoteModel): HomeScreenStates()
    object OnAddClick: HomeScreenStates()
}