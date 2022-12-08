package com.henriquevieira.notes.features.main.ui

import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.data.model.Note

data class MainViewState(
    val noteType: NoteTypes = NoteTypes.Primary,
    val contentText: String = "",
    val note: Note = Note(),
)