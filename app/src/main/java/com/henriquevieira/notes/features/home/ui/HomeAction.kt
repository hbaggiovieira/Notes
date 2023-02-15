package com.henriquevieira.notes.features.home.ui

import com.henriquevieira.notes.data.model.Note

sealed class HomeAction {
    data class CardClick(val noteId: Int) : HomeAction()
    data class CardLongPress(val note: Note) : HomeAction()
    data class DeleteConfirm(val note: Note) : HomeAction()
    object FetchData : HomeAction()
    object AddClick : HomeAction()
    object ListClick : HomeAction()
}