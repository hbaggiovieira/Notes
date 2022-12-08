package com.henriquevieira.notes.features.home.ui

import com.henriquevieira.notes.data.model.Note

sealed class HomeEvents {
    data class CardClick(val noteId: Int) : HomeEvents()
    data class CardLongPress(val note: Note) : HomeEvents()
    data class DeleteConfirm(val note: Note) : HomeEvents()
    object FetchData : HomeEvents()
    object AddClick : HomeEvents()
}