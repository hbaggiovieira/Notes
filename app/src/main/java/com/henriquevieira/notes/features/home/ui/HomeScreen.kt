package com.henriquevieira.notes.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.henriquevieira.commonsui.button.CustomCircleIconButton
import com.henriquevieira.commonsui.card.CustomCard
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.commonsui.utils.ColorUtils
import com.henriquevieira.notes.features.home.model.NoteModel

@Composable
fun HomeScreen(
    uiState: HomeViewState,
    onUiEvent: (event: HomeEvents) -> Unit,
) {
    ConstraintLayout (
        modifier = Modifier.fillMaxSize()
            ) {
        val (notesList, addButton) = createRefs()

        CustomList(
            modifier = Modifier.constrainAs(notesList) {
                top.linkTo(parent.top)
                width = Dimension.fillToConstraints
            },
            onUiEvent = onUiEvent,
            //ToDo GetFrom Database
            notes = uiState.notesList
        )

        AddButton(modifier = Modifier.constrainAs(addButton) {
            centerHorizontallyTo(parent)
            bottom.linkTo(parent.bottom)
        })
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
private fun CustomList(
    modifier: Modifier = Modifier,
    onUiEvent: (event: HomeEvents) -> Unit,
    notes: List<NoteModel>? = null,
) {
    Column(modifier) {
        notes?.forEach {
            HomeCustomCard(noteModel = it,
                onClick = {
                    onUiEvent(HomeEvents.OnCardClick(it))
                })
        }
    }
}

@Composable
private fun AddButton(modifier: Modifier = Modifier) {
    CustomCircleIconButton(
        modifier = modifier.testTag("ADD_BUTTON_TAG"),
        imageVector = Icons.Rounded.Add,
        imageColor = Color.Black,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentDescription = "Add button"
    ) {

    }
}