package com.henriquevieira.notes.features.main.ui

import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.features.home.model.NoteModel

data class MainViewState(
    val noteType: NoteTypes = NoteTypes.Primary,
    val contentText: String = "",
    val noteModel: NoteModel = NoteModel()
)