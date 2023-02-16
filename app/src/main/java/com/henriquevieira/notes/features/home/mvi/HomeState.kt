package com.henriquevieira.notes.features.home.mvi

import com.henriquevieira.notes.data.model.Note

data class HomeState(
    val notesList: List<Note>? = null,
    val alertDialogState: Boolean = false,
)