package com.henriquevieira.commonsui.textinput

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.commonsui.ds.md_theme_light_primaryContainer
import com.henriquevieira.commonsui.utils.noteType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseNote(
    modifier: Modifier = Modifier,
    noteTypes: NoteTypes = NoteTypes.Primary,
    text: MutableState<String>,
    label: String? = null,
) {

    val textFieldColors = when (noteTypes) {
        NoteTypes.Primary -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                containerColor = md_theme_light_primaryContainer
            )
        }
        NoteTypes.Red -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                cursorColor = Color.White,
                containerColor = Color.Red
            )
        }
        NoteTypes.Green -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                containerColor = Color.Green
            )
        }
        NoteTypes.Yellow -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                containerColor = Color.Yellow
            )
        }
        NoteTypes.Blue -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                cursorColor = Color.White,
                containerColor = Color.Blue
            )
        }
    }

    AppTheme {
        TextField(
            modifier = modifier.fillMaxSize()
                .semantics {
                    noteType = noteTypes.toString()
                },
            value = text.value,
            colors = textFieldColors,
            onValueChange = {
                text.value = it
            },
            label = {
                label?.let {
                    Text(label)
                }
            }
        )
    }
}