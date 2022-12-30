package com.henriquevieira.commonsui.textinput

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.henriquevieira.commonsui.ds.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseNoteTitle(
    modifier: Modifier = Modifier,
    text: String,
    placeHolder: String = "",
    onValueChange: ((str: String) -> Unit)? = null
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor = Color.Black,
        cursorColor = Color.Black,
        containerColor = Color.White
    )

    AppTheme {
        TextField(
            modifier = modifier,
            value = text,
            colors = textFieldColors,
            onValueChange = {
                onValueChange?.invoke(it)
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray,
                        fontSize = 28.sp
                    ),
                    text = placeHolder
                )
            }
        )
    }
}