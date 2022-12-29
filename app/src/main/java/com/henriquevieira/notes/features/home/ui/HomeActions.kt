package com.henriquevieira.notes.features.home.ui

import com.henriquevieira.notes.data.model.Note

sealed class HomeActions {
    data class CardClick(val noteId: Int) : HomeActions()
    data class CardLongPress(val note: Note) : HomeActions()
    data class DeleteConfirm(val note: Note) : HomeActions()
    object FetchData : HomeActions()
    object AddClick : HomeActions()
}