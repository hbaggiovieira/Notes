package com.henriquevieira.commonsui.textinput

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.henriquevieira.commonsui.ds.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextInput(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    label: String? = null,
) {
    AppTheme {
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
}