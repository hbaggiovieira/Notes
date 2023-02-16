package com.henriquevieira.notes.features.home.mvi

import com.henriquevieira.notes.data.model.Note


sealed class HomeResult {
    data class OnCardClick(val noteId: Int) : HomeResult()
    object OnCloseClick : HomeResult()
    object OnAddClick : HomeResult()
    object OnListClick : HomeResult()
    object OnFetchError : HomeResult()
    object OnDeleteError : HomeResult()
    object OnDeleteSuccess : HomeResult()
    data class OnShowAlertDialog(val note: Note) : HomeResult()
}