package com.henriquevieira.notes.features.home.ui

import com.henriquevieira.notes.features.home.model.NoteModel

data class HomeViewState(
    val notesList: List<NoteModel>? = null
)