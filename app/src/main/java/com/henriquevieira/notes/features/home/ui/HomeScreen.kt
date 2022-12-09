package com.henriquevieira.notes.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.henriquevieira.commonsui.button.CustomCircleIconButton
import com.henriquevieira.commonsui.card.CustomCard
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.commonsui.utils.ColorUtils
import com.henriquevieira.notes.data.model.Note

@Composable
fun HomeScreen(
    uiState: HomeViewState,
    onUiEvent: (event: HomeEvents) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (notesList, addButton) = createRefs()

        CustomList(
            modifier = Modifier
                .testTag("NOTES_LIST_TAG")
                .constrainAs(notesList) {
                    top.linkTo(parent.top)
                    bottom.linkTo(addButton.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                },
            uiState = uiState,
            onUiEvent = onUiEvent
        )

        AddButton(
            modifier = Modifier
                .testTag("ADD_BUTTON_TAG")
                .constrainAs(addButton) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom, 2.dp)
                },
            onUiEvent = onUiEvent
        )
    }
}

@Composable
private fun HomeCustomCard(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: (() -> Unit)? = null,
    onLongPress: (() -> Unit)? = null,
) {
    CustomCard(
        modifier = modifier.padding(vertical = 8.dp),
        backgroundColor = ColorUtils.getBackgroundColorByType(note.noteType),
        contentColor = ColorUtils.getContentColorByType(note.noteType),
        onLongPress = {
            onLongPress?.invoke()
        },
        onClick = {
            onClick?.invoke()
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = note.title
            )
        }
    }
}

@Composable
private fun CustomList(
    modifier: Modifier = Modifier,
    uiState: HomeViewState,
    onUiEvent: (event: HomeEvents) -> Unit,
) {
    LazyColumn(modifier) {
        uiState.notesList?.let { notes ->
            items(uiState.notesList.size) { index ->
                HomeCustomCard(
                    note = notes[index],
                    onLongPress = {
                        onUiEvent(HomeEvents.CardLongPress(notes[index]))
                    },
                    onClick = {
                        onUiEvent(HomeEvents.CardClick(notes[index].id))
                    }
                )
            }
        }
    }
}

@Composable
private fun AddButton(
    modifier: Modifier = Modifier,
    onUiEvent: (event: HomeEvents) -> Unit,
) {
    CustomCircleIconButton(
        modifier = modifier.testTag("ADD_BUTTON_TAG"),
        imageVector = Icons.Rounded.Add,
        imageColor = Color.Black,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentDescription = "Add"
    ) {
        onUiEvent(HomeEvents.AddClick)
    }
}