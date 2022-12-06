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
import com.henriquevieira.commonsui.button.CustomCircleIconButton
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

        ButtonRow(modifier = Modifier
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
private fun ButtonRow(
    modifier: Modifier,
    onUiEvent: (event: MainEvents) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomCircleIconButton(
            imageVector = Icons.Rounded.Done,
            imageColor = Color.Black,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            contentDescription = "Primary"
        ) {
            onUiEvent(MainEvents.OnPrimaryColorSelected)
        }

        CustomCircleIconButton(
            imageVector = Icons.Rounded.Done,
            imageColor = Color.White,
            backgroundColor = Color.Red,
            contentDescription = "Red"
        ) {
            onUiEvent(MainEvents.OnRedColorSelected)
        }

        CustomCircleIconButton(
            imageVector = Icons.Rounded.Done,
            imageColor = Color.Black,
            backgroundColor = Color.Green,
            contentDescription = "Green"
        ) {
            onUiEvent(MainEvents.OnGreenColorSelected)
        }

        CustomCircleIconButton(
            imageVector = Icons.Rounded.Done,
            imageColor = Color.Black,
            backgroundColor = Color.Yellow,
            contentDescription = "Yellow"
        ) {
            onUiEvent(MainEvents.OnYellowColorSelected)
        }

        CustomCircleIconButton(
            imageVector = Icons.Rounded.Done,
            imageColor = Color.White,
            backgroundColor = Color.Blue,
            contentDescription = "Blue"
        ) {
            onUiEvent(MainEvents.OnBlueColorSelected)
        }
    }
}