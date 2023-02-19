package com.henriquevieira.notes.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.henriquevieira.commonsui.button.CustomCircleIconButton
import com.henriquevieira.commonsui.card.CustomCard
import com.henriquevieira.commonsui.header.CustomHeader
import com.henriquevieira.commonsui.utils.ColorUtils
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.features.home.mvi.HomeAction
import com.henriquevieira.notes.features.home.mvi.HomeState

@Composable
fun HomeScreen(
    uiState: HomeState,
    onUiAction: (event: HomeAction) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (headerRef, notesListRef, addButtonRef, listButtonRef, newAlertRef) = createRefs()

        CustomHeader(
            title = "Notes",
            modifier = Modifier.constrainAs(headerRef) {
                top.linkTo(parent.top)
                width = Dimension.matchParent
            },
            onCloseButtonClick = { onUiAction.invoke(HomeAction.CloseClick) }
        )

        CustomList(
            modifier = Modifier
                .testTag("NOTES_LIST_TAG")
                .constrainAs(notesListRef) {
                    top.linkTo(headerRef.bottom)
                    bottom.linkTo(addButtonRef.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                },
            uiState = uiState,
            onUiEvent = onUiAction
        )

        AddButton(
            modifier = Modifier
                .testTag("ADD_BUTTON_TAG")
                .constrainAs(addButtonRef) {
                    end.linkTo(listButtonRef.start, 4.dp)
                    bottom.linkTo(parent.bottom, 2.dp)
                },
            onUiEvent = onUiAction
        )

        Text(
            text = "NEW",
            color = Color.Red,
            modifier = Modifier.constrainAs(newAlertRef){
                bottom.linkTo(listButtonRef.top, 1.dp)
                start.linkTo(listButtonRef.start)
                end.linkTo(listButtonRef.end)
            }
        )
        ListButton(
            modifier = Modifier.testTag("LIST_BUTTON_TAG")
                .constrainAs(listButtonRef) {
                    end.linkTo(parent.end, 4.dp)
                    top.linkTo(addButtonRef.top)
                    bottom.linkTo(addButtonRef.bottom)
                },
            onUiEvent = onUiAction
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
    uiState: HomeState,
    onUiEvent: (event: HomeAction) -> Unit,
) {
    LazyColumn(modifier) {
        uiState.notesList?.let { notes ->
            items(uiState.notesList.size) { index ->
                HomeCustomCard(
                    note = notes[index],
                    onLongPress = {
                        onUiEvent(HomeAction.CardLongPress(notes[index]))
                    },
                    onClick = {
                        onUiEvent(HomeAction.CardClick(notes[index].id))
                    }
                )
            }
        }
    }
}

@Composable
private fun AddButton(
    modifier: Modifier = Modifier,
    onUiEvent: (event: HomeAction) -> Unit,
) {
    CustomCircleIconButton(
        modifier = modifier.testTag("ADD_BUTTON_TAG"),
        imageVector = Icons.Rounded.Add,
        imageColor = Color.Black,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentDescription = "Add"
    ) {
        onUiEvent(HomeAction.AddClick)
    }
}

@Composable
private fun ListButton(
    modifier: Modifier = Modifier,
    onUiEvent: (event: HomeAction) -> Unit,
) {
    CustomCircleIconButton(
        modifier = modifier.testTag("ADD_BUTTON_TAG"),
        imageVector = Icons.Rounded.List,
        imageColor = Color.Black,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentDescription = "Add"
    ) {
        onUiEvent(HomeAction.ListClick)
    }
}