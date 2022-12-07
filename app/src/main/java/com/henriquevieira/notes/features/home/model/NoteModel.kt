package com.henriquevieira.notes.features.home.model

import android.os.Parcelable
import com.henriquevieira.commonsui.textinput.NoteTypes
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteModel(
    val id: Int = 0,
    val title: String = "",
    val contentText: String = "",
    val noteType: NoteTypes = NoteTypes.Primary,
) : Parcelable
