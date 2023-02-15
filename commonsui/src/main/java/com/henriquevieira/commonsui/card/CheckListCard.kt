package com.henriquevieira.commonsui.card

import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun CheckListCard(
    modifier: Modifier = Modifier,
    title: String = "",
    text: String = "",
) {
    ConstraintLayout(modifier = modifier) {
        val (checkBoxRef, titleRef, contentRef) = createRefs()

        Checkbox(
            checked = false,
            onCheckedChange = null,
            enabled = true,
            modifier = Modifier.constrainAs(checkBoxRef) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start, 4.dp)
            },
        )

        Text(
            text = title,
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top)
                bottom.linkTo(contentRef.top)
                start.linkTo(checkBoxRef.end, 4.dp)
                end.linkTo(parent.end, 4.dp)
            },
        )

        Text(
            text = text,
            modifier = Modifier.constrainAs(contentRef) {
                bottom.linkTo(parent.bottom)
                top.linkTo(titleRef.bottom)
                start.linkTo(checkBoxRef.end, 4.dp)
                end.linkTo(parent.end, 4.dp)
            }
        )

    }
}

@Preview
@Composable
fun PreviewCheckListCard() {
    CheckListCard(
        title = "Title",
        text = "Content Text"
    )
}