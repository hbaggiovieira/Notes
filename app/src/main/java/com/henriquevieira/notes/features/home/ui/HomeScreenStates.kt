package com.henriquevieira.notes.features.home.ui

import com.henriquevieira.notes.data.model.Note


sealed class HomeScreenStates {
    data class OnCardClick(val selectedNote: Note): HomeScreenStates()
    object OnAddClick: HomeScreenStates()
    object OnFetchSuccess: HomeScreenStates()
}