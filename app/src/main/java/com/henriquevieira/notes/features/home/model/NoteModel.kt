package com.henriquevieira.notes.features.home.model

import com.henriquevieira.commonsui.textinput.NoteTypes

data class NoteModel(
    val id: Int = 0,
    val title: String = "",
    val noteType: NoteTypes = NoteTypes.Primary,
    val contentText: String = ""
)
