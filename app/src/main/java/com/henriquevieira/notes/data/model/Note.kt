package com.henriquevieira.notes.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.henriquevieira.commonsui.textinput.NoteTypes
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "contentText") val contentText: String? = null,
    @ColumnInfo(name = "noteType") val noteType: NoteTypes? = null,
) : Parcelable
