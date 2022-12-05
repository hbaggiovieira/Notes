package com.henriquevieira.notes.features.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.henriquevieira.commonsui.textinput.CustomInputType
import com.henriquevieira.commonsui.textinput.CustomTextInput
import com.henriquevieira.notes.features.main.viewmodel.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel) {

    ConstraintLayout(modifier = Modifier.fillMaxSize().background(Color.White)) {
        val (title, textField, button) = createRefs()

        Text(
            modifier = Modifier.constrainAs(title) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            },
            text = "Title"
        )

        val text = remember { mutableStateOf("") }

        CustomTextInput(
            modifier = Modifier.constrainAs(textField)
            {
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
                top.linkTo(title.bottom, 8.dp)
                bottom.linkTo(button.top, 8.dp)
            },
            inputType = CustomInputType.Yellow,
            text = text
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