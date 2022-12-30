package com.henriquevieira.notes.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.henriquevieira.commonsui.textinput.NoteType
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "contentText") val contentText: String = "",
    @ColumnInfo(name = "noteType") val noteType: NoteType = NoteType.Primary,
) : Parcelable
