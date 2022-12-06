package com.henriquevieira.notes.features.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.henriquevieira.commonsui.ds.md_theme_light_primaryContainer
import com.henriquevieira.commonsui.textinput.CustomTextInput
import com.henriquevieira.notes.features.main.viewmodel.MainScreenEvent

@Composable
fun MainScreen(
    uiState: MainViewState,
    onUiEvent: (event: MainScreenEvent) -> Unit,
) {

    ConstraintLayout(modifier = Modifier.fillMaxSize().background(Color.White)) {
        val (title, textField, buttonRow, button) = createRefs()

        Text(
            modifier = Modifier.constrainAs(title) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            },
            text = "Title"
        )

        val text = remember { mutableStateOf(uiState.contentText) }

        CustomTextInput(
            modifier = Modifier.constrainAs(textField)
            {
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
                top.linkTo(title.bottom)
                bottom.linkTo(buttonRow.top)
            }
                .padding(8.dp),
            inputType = uiState.noteColor,
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
                onUiEvent(MainScreenEvent.OnClickSaveButton(text.value))
            }
        )
    }
}

@Composable
private fun ColorButtons(
    modifier: Modifier,
    onUiEvent: (event: MainScreenEvent) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = {
                onUiEvent(MainScreenEvent.OnPrimaryColorSelected)
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier
                    .background(shape = CircleShape, color = md_theme_light_primaryContainer)
                    .size(50.dp)
            )
        }

        IconButton(
            onClick = {
                onUiEvent(MainScreenEvent.OnRedColorSelected)
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .background(shape = CircleShape, color = Color.Red)
                    .size(50.dp)
            )
        }

        IconButton(
            onClick = {
                onUiEvent(MainScreenEvent.OnGreenColorSelected)
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier
                    .background(shape = CircleShape, color = Color.Green)
                    .size(50.dp)
            )
        }

        IconButton(
            onClick = {
                onUiEvent(MainScreenEvent.OnYellowColorSelected)
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier
                    .background(shape = CircleShape, color = Color.Yellow)
                    .size(50.dp)
            )
        }

        IconButton(
            onClick = {
                onUiEvent(MainScreenEvent.OnBlueColorSelected)
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .background(shape = CircleShape, color = Color.Blue)
                    .size(50.dp)
            )
        }
    }
}