package com.henriquevieira.notes.features.note.ui

import com.henriquevieira.notes.data.model.Note

data class NoteViewState(
    val note: Note = Note(),
)