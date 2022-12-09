package com.henriquevieira.notes.features.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.requestFocus
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.henriquevieira.commonsui.button.CustomCircleIconButton
import com.henriquevieira.commonsui.ds.color_dark_red
import com.henriquevieira.commonsui.ds.color_mid_yellow
import com.henriquevieira.commonsui.textinput.BaseNote
import com.henriquevieira.commonsui.textinput.BaseNoteTitle
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.R
import com.henriquevieira.notes.data.model.Note

@Composable
fun MainScreen(
    uiState: MainViewState,
    onUiEvent: (event: MainEvents) -> Unit,
) {

    ConstraintLayout(modifier = Modifier.fillMaxSize().background(Color.White)) {
        val (title, textField, buttonRow, buttonClear, button) = createRefs()

        val text = remember { mutableStateOf(uiState.note.contentText) }
        val titleText = remember { mutableStateOf(uiState.note.title) }

        BaseNoteTitle(
            modifier = Modifier
                .testTag("TITLE_TAG")
                .constrainAs(title) {
                    width = Dimension.matchParent
                    top.linkTo(parent.top)
                },
            text = titleText,
            placeHolder = stringResource(R.string.title)
        )

        BaseNote(
            modifier = Modifier
                .semantics {
                    this.contentDescription = text.value.ifEmpty { "Note" }
                }
                .focusTarget()
                .padding(8.dp)
                .testTag("TEXT_FIELD_TAG")
                .constrainAs(textField) {
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                    top.linkTo(title.bottom)
                    bottom.linkTo(buttonRow.top)
                },
            noteTypes = uiState.note.noteType,
            text = text
        )

        ButtonRow(modifier = Modifier
            .constrainAs(buttonRow) {
                width = Dimension.matchParent
                bottom.linkTo(button.top, 2.dp)
                top.linkTo(textField.bottom, 2.dp)
            },
            onUiEvent = onUiEvent
        )

        IconButton(modifier = Modifier
            .testTag("CLEAR_TEXT_BUTTON_TAG")
            .constrainAs(buttonClear) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                bottom.linkTo(textField.bottom, 16.dp)
                end.linkTo(textField.end, 16.dp)
            }, onClick = {
            titleText.value = ""
            text.value = ""
        }) {
            Box(modifier = Modifier
                .size(45.dp)
                .background(
                    color = Color.LightGray,
                    shape = CircleShape
                ),
                contentAlignment = Alignment.Center,
                content = {
                    Icon(imageVector = Icons.Rounded.Clear, contentDescription = "Clear Text")
                })
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("SAVE_BUTTON_TAG")
                .constrainAs(button) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom)
                },
            content = {
                Text(stringResource(R.string.save))
            },
            onClick = {
                onUiEvent(MainEvents.ClickSaveButton(
                    Note(
                        id = uiState.note.id,
                        title = titleText.value,
                        contentText = text.value,
                        noteType = uiState.note.noteType
                    )
                ))
            }
        )
    }
}

@Composable
private fun ButtonRow(
    modifier: Modifier,
    onUiEvent: (event: MainEvents) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomCircleIconButton(
            modifier = Modifier.testTag("PRIMARY_COLOR_BUTTON_TAG"),
            imageVector = Icons.Rounded.Done,
            imageColor = Color.Black,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            contentDescription = "Primary"
        ) {
            onUiEvent(MainEvents.PrimaryColorSelected)
        }

        CustomCircleIconButton(
            modifier = Modifier.testTag("RED_COLOR_BUTTON_TAG"),
            imageVector = Icons.Rounded.Done,
            imageColor = Color.White,
            backgroundColor = color_dark_red,
            contentDescription = "Red"
        ) {
            onUiEvent(MainEvents.RedColorSelected)
        }

        CustomCircleIconButton(
            modifier = Modifier.testTag("GREEN_COLOR_BUTTON_TAG"),
            imageVector = Icons.Rounded.Done,
            imageColor = Color.Black,
            backgroundColor = Color.Green,
            contentDescription = "Green"
        ) {
            onUiEvent(MainEvents.GreenColorSelected)
        }

        CustomCircleIconButton(
            modifier = Modifier.testTag("YELLOW_COLOR_BUTTON_TAG"),
            imageVector = Icons.Rounded.Done,
            imageColor = Color.Black,
            backgroundColor = color_mid_yellow,
            contentDescription = "Yellow"
        ) {
            onUiEvent(MainEvents.YellowColorSelected)
        }

        CustomCircleIconButton(
            modifier = Modifier.testTag("BLUE_COLOR_BUTTON_TAG"),
            imageVector = Icons.Rounded.Done,
            imageColor = Color.White,
            backgroundColor = Color.Blue,
            contentDescription = "Blue"
        ) {
            onUiEvent(MainEvents.BlueColorSelected)
        }
    }
}