package com.henriquevieira.commonsui.textinput

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.henriquevieira.commonsui.ds.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseNoteTitle(
    modifier: Modifier = Modifier,
    text: MutableState<String?>,
    placeHolder: String = "",
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor = Color.Black,
        cursorColor = Color.Black,
        containerColor = Color.White
    )

    AppTheme {
        TextField(
            modifier = modifier,
            value = text.value ?: "",
            colors = textFieldColors,
            onValueChange = {
                text.value = it
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center
            ),
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray
                    ),
                    text = placeHolder
                )
            }
        )
    }
}