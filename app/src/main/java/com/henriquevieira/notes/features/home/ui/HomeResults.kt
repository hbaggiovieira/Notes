package com.henriquevieira.notes.features.home.ui

import com.henriquevieira.notes.data.model.Note


sealed class HomeResults {
    data class OnCardClick(val noteId: Int) : HomeResults()
    object OnAddClick : HomeResults()
    object OnFetchError : HomeResults()
    object OnDeleteError : HomeResults()
    object OnDeleteSuccess : HomeResults()
    data class OnShowAlertDialog(val note: Note) : HomeResults()
}