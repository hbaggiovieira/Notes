package com.henriquevieira.notes.features.home.ui

import com.henriquevieira.notes.data.model.Note


data class HomeStates(
    val notesList: List<Note>? = null,
    val alertDialogState: Boolean = false,
)