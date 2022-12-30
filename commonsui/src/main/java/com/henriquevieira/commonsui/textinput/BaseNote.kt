package com.henriquevieira.commonsui.textinput

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.henriquevieira.commonsui.ds.*
import com.henriquevieira.commonsui.utils.noteType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseNote(
    modifier: Modifier = Modifier,
    noteType: NoteType = NoteType.Primary,
    text: String,
    label: String? = null,
    onValueChange: ((str: String) -> Unit)? = null
) {

    val textFieldColors = when (noteType) {
        NoteType.Primary -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                containerColor = color_card_default
            )
        }
        NoteType.Red -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                cursorColor = Color.White,
                containerColor = color_card_red
            )
        }
        NoteType.Green -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                containerColor = color_card_green
            )
        }
        NoteType.Yellow -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                containerColor = color_card_yellow
            )
        }
        NoteType.Blue -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                cursorColor = Color.White,
                containerColor = color_card_blue
            )
        }
    }

    AppTheme {
        TextField(
            modifier = modifier.fillMaxSize()
                .semantics {
                    this.noteType = noteType.toString()
                },
            value = text,
            colors = textFieldColors,
            textStyle = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold
            ),
            onValueChange = {
                onValueChange?.invoke(it)
            },
            label = {
                label?.let {
                    Text(label)
                }
            }
        )
    }
}