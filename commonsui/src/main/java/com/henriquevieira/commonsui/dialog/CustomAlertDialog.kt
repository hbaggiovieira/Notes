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
    title: String = "",
    text: String = "",
    confirmButtonLabel: String = "",
    dismissButtonLabel: String = "",
) {
    AlertDialog(
        onDismissRequest = {
            isEnabled.value = false
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text)
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmClick?.invoke()
                }) {
                Text(confirmButtonLabel)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissClick?.invoke()
                }) {
                Text(dismissButtonLabel)
            }
        }
    )
}