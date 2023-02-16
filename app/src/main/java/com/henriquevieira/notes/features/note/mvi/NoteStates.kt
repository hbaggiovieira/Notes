package com.henriquevieira.notes.features.note.mvi

import com.henriquevieira.notes.data.model.Note

data class NoteStates(
    val note: Note = Note(),
)