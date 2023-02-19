package com.henriquevieira.notes.features.checklist.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class CheckListItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "content") val content: String = "",
    @ColumnInfo(name = "isChecked") var isChecked: Boolean = false,
) : Parcelable
