package com.henriquevieira.notes.features.note.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.henriquevieira.commonsui.button.CustomCircleIconButton
import com.henriquevieira.commonsui.ds.*
import com.henriquevieira.commonsui.textinput.BaseNote
import com.henriquevieira.commonsui.textinput.BaseNoteTitle
import com.henriquevieira.commonsui.textinput.NoteType
import com.henriquevieira.notes.R
import com.henriquevieira.notes.data.model.Note

@Composable
fun NoteScreen(
    uiState: NoteStates,
    onUiEvent: (event: NoteActions) -> Unit,
) {

    ConstraintLayout(modifier = Modifier.fillMaxSize().background(Color.White)) {
        val (title, textField, buttonRow, buttonClear, button) = createRefs()

        BaseNoteTitle(
            modifier = Modifier
                .testTag("TITLE_TAG")
                .constrainAs(title) {
                    width = Dimension.matchParent
                    top.linkTo(parent.top)
                },
            text = uiState.note.title,
            placeHolder = stringResource(R.string.title),
            onValueChange = {
                onUiEvent.invoke(NoteActions.UpdateTitleText(
                    title = it
                ))
            }
        )

        BaseNote(
            modifier = Modifier
                .semantics {
                    this.contentDescription = uiState.note.contentText.ifEmpty { "Note" }
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
            noteType = uiState.note.noteType,
            text = uiState.note.contentText,
            onValueChange = {
                onUiEvent.invoke(NoteActions.UpdateContentText(
                    text = it
                ))
            }
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
            onUiEvent.invoke(NoteActions.ClickClearButton(Note(
                noteType = uiState.note.noteType
            )))
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
                onUiEvent(NoteActions.ClickSaveButton(
                    Note(
                        id = uiState.note.id,
                        title = uiState.note.title,
                        contentText = uiState.note.contentText,
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
    onUiEvent: (event: NoteActions) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomCircleIconButton(
            modifier = Modifier.testTag("PRIMARY_COLOR_BUTTON_TAG"),
            imageVector = Icons.Rounded.Done,
            imageColor = Color.Black,
            backgroundColor = color_card_default,
            contentDescription = "Primary"
        ) {
            onUiEvent(NoteActions.NoteTypeClick(
                noteType = NoteType.Primary
            ))
        }

        CustomCircleIconButton(
            modifier = Modifier.testTag("RED_COLOR_BUTTON_TAG"),
            imageVector = Icons.Rounded.Done,
            imageColor = Color.White,
            backgroundColor = color_card_red,
            contentDescription = "Red"
        ) {
            onUiEvent(NoteActions.NoteTypeClick(
                noteType = NoteType.Red
            ))
        }

        CustomCircleIconButton(
            modifier = Modifier.testTag("GREEN_COLOR_BUTTON_TAG"),
            imageVector = Icons.Rounded.Done,
            imageColor = Color.Black,
            backgroundColor = color_card_green,
            contentDescription = "Green"
        ) {
            onUiEvent(NoteActions.NoteTypeClick(
                noteType = NoteType.Green
            ))
        }

        CustomCircleIconButton(
            modifier = Modifier.testTag("YELLOW_COLOR_BUTTON_TAG"),
            imageVector = Icons.Rounded.Done,
            imageColor = Color.Black,
            backgroundColor = color_card_yellow,
            contentDescription = "Yellow"
        ) {
            onUiEvent(NoteActions.NoteTypeClick(
                noteType = NoteType.Yellow
            ))
        }

        CustomCircleIconButton(
            modifier = Modifier.testTag("BLUE_COLOR_BUTTON_TAG"),
            imageVector = Icons.Rounded.Done,
            imageColor = Color.White,
            backgroundColor = color_card_blue,
            contentDescription = "Blue"
        ) {
            onUiEvent(NoteActions.NoteTypeClick(
                noteType = NoteType.Blue

            ))
        }
    }
}