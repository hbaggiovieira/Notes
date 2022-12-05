package com.henriquevieira.commonsui.textinput

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.commonsui.ds.md_theme_light_primaryContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextInput(
    modifier: Modifier = Modifier,
    inputType: CustomInputType = CustomInputType.Primary,
    text: MutableState<String>,
    label: String? = null,
) {

    val textFieldColors = when (inputType) {
        CustomInputType.Primary -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = md_theme_light_primaryContainer
            )
        }
        CustomInputType.Red -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                containerColor = Color.Red
            )
        }
        CustomInputType.Green -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.Green
            )
        }
        CustomInputType.Yellow -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.Yellow
            )
        }
        CustomInputType.Blue -> {
            TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                containerColor = Color.Blue
            )
        }
    }

    AppTheme {
        TextField(
            value = text.value,
            colors = textFieldColors,
            onValueChange = {
                text.value = it
            },
            label = {
                label?.let {
                    Text(label)
                }
            },
            modifier = modifier.fillMaxSize()
        )
    }
}