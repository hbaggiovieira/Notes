package com.henriquevieira.commonsui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun CustomAlertDialog(
    isEnabled: MutableState<Boolean>,
    onConfirmClick: (() -> Unit)? = null,
    onDismissClick: (() -> Unit)? = null,
) {
    AlertDialog(
        onDismissRequest = {
            isEnabled.value = false
        },
        title = {
            Text(text = "Delete Note?")
        },
        text = {
            Text("Are you sure you want to delete this note?")
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmClick?.invoke()
                }) {
                Text("Ok")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissClick?.invoke()
                }) {
                Text("Cancel")
            }
        }
    )
}