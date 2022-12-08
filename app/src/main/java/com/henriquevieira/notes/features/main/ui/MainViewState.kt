package com.henriquevieira.notes.features.main.ui

import com.henriquevieira.notes.data.model.Note

data class MainViewState(
    val note: Note = Note(),
)