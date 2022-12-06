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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.henriquevieira.commonsui.textinput.BaseNote

@Composable
fun MainScreen(
    uiState: MainViewState,
    onUiEvent: (event: MainEvents) -> Unit,
) {

    ConstraintLayout(modifier = Modifier.fillMaxSize().background(Color.White)) {
        val (title, textField, buttonRow, buttonClear, button) = createRefs()

        Text(
            modifier = Modifier.constrainAs(title) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            },
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary,
            text = "My Notes",
            fontWeight = FontWeight.Bold
        )

        val text = remember { mutableStateOf(uiState.contentText) }

        BaseNote(
            modifier = Modifier.constrainAs(textField)
            {
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
                top.linkTo(title.bottom)
                bottom.linkTo(buttonRow.top)
            }
                .padding(8.dp),
            inputType = uiState.noteType,
            text = text
        )

        ColorButtons(modifier = Modifier
            .constrainAs(buttonRow) {
                width = Dimension.matchParent
                bottom.linkTo(button.top, 2.dp)
                top.linkTo(textField.bottom, 2.dp)
            },
            onUiEvent = onUiEvent
        )

        IconButton(modifier = Modifier
            .constrainAs(buttonClear) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                bottom.linkTo(textField.bottom, 16.dp)
                end.linkTo(textField.end, 16.dp)
            }, onClick = {
            onUiEvent(MainEvents.OnClickClearButton)
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
                .constrainAs(button) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom)
                },
            content = {
                Text("Save")
            },
            onClick = {
                onUiEvent(MainEvents.OnClickSaveButton(text.value))
            }
        )
    }
}

@Composable
private fun ColorButtons(
    modifier: Modifier,
    onUiEvent: (event: MainEvents) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = {
                onUiEvent(MainEvents.OnPrimaryColorSelected)
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "Primary Background",
                tint = Color.Black,
                modifier = Modifier
                    .background(shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer)
                    .size(50.dp)
            )
        }

        IconButton(
            onClick = {
                onUiEvent(MainEvents.OnRedColorSelected)
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "Red",
                tint = Color.White,
                modifier = Modifier
                    .background(shape = CircleShape, color = Color.Red)
                    .size(50.dp)
            )
        }

        IconButton(
            onClick = {
                onUiEvent(MainEvents.OnGreenColorSelected)
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "Green",
                tint = Color.Black,
                modifier = Modifier
                    .background(shape = CircleShape, color = Color.Green)
                    .size(50.dp)
            )
        }

        IconButton(
            onClick = {
                onUiEvent(MainEvents.OnYellowColorSelected)
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "Yellow",
                tint = Color.Black,
                modifier = Modifier
                    .background(shape = CircleShape, color = Color.Yellow)
                    .size(50.dp)
            )
        }

        IconButton(
            onClick = {
                onUiEvent(MainEvents.OnBlueColorSelected)
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "Blue",
                tint = Color.White,
                modifier = Modifier
                    .background(shape = CircleShape, color = Color.Blue)
                    .size(50.dp)
            )
        }
    }
}