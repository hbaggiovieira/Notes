package com.henriquevieira.notes.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.henriquevieira.commonsui.card.CustomCard
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.commonsui.utils.ColorUtils
import com.henriquevieira.notes.features.home.model.NoteModel

@Composable
fun HomeScreen() {
    ConstraintLayout {
        //ToDo GetFrom Database
        CustomList(
            notes = listOf(
                NoteModel(
                    title = "A",
                    noteType = NoteTypes.Primary
                ),
                NoteModel(
                    title = "B",
                    noteType = NoteTypes.Red
                ),
                NoteModel(
                    title = "C",
                    noteType = NoteTypes.Green
                ),
                NoteModel(
                    title = "D",
                    noteType = NoteTypes.Yellow
                ),
                NoteModel(
                    title = "E",
                    noteType = NoteTypes.Blue
                ),
            )
        )
    }
}

@Composable
private fun HomeCustomCard(
    modifier: Modifier = Modifier,
    noteModel: NoteModel,
    onClick: (() -> Unit)? = null,
) {
    CustomCard(
        modifier = modifier.padding(vertical = 8.dp),
        backgroundColor = ColorUtils.getBackgroundColorByType(noteModel.noteType),
        contentColor = ColorUtils.getContentColorByType(noteModel.noteType),
        onClick = {
            onClick?.invoke()
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(noteModel.title)
        }
    }
}

@Composable
private fun CustomList(notes: List<NoteModel>? = null) {
    Column {
        notes?.forEach {
            HomeCustomCard(noteModel = it,
                onClick = {
                    //ToDo Navigate to Selected Note
                })
        }
    }
}