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
    selectedColor: Color = md_theme_light_primaryContainer,
    text: MutableState<String>,
    label: String? = null,
) {
    AppTheme {
        TextField(
            value = text.value,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = selectedColor
            ),
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