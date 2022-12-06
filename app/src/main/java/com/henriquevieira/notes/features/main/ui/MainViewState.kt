package com.henriquevieira.notes.features.main.ui

import com.henriquevieira.commonsui.textinput.NoteTypes

data class MainViewState(
    val noteType: NoteTypes = NoteTypes.Primary,
    val contentText: String = ""
)