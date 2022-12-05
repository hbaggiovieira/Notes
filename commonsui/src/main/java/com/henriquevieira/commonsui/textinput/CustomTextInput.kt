package com.henriquevieira.commonsui.textinput

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextInput(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    label: String? = null,
) {
    TextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        label = {
            label?.let {
                Text(label)
            }
        },
        modifier = modifier
    )
}