package com.henriquevieira.notes.features.home.ui

import com.henriquevieira.notes.data.model.Note


sealed class HomeScreenStates {
    data class OnCardClick(val noteId: Int) : HomeScreenStates()
    object OnAddClick : HomeScreenStates()
    object OnFetchSuccess : HomeScreenStates()
    object OnDeleteError : HomeScreenStates()
    object OnDeleteSuccess : HomeScreenStates()
    data class OnShowAlertDialog(val note: Note) : HomeScreenStates()
}