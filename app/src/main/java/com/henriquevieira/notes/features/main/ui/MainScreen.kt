package com.henriquevieira.notes.features.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    ConstraintLayout(modifier = Modifier.fillMaxSize().background(Color.White)) {
        val (title, textField, button) = createRefs()

        Text(
            modifier = Modifier.constrainAs(title) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            },
            text = "Title"
        )

        var text by rememberSaveable { mutableStateOf("Text") }

        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text("Label") },
            modifier = Modifier.constrainAs(textField) {
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
                top.linkTo(title.bottom, 8.dp)
                bottom.linkTo(button.top, 8.dp)
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom)
                },
            content = {
                Text("ButtonText")
            },
            onClick = {}
        )
    }
}