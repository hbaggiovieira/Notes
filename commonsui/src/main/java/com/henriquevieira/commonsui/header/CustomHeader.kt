package com.henriquevieira.commonsui.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.commonsui.ds.md_theme_light_onSecondaryContainer

@Composable
fun CustomHeader(
    modifier: Modifier = Modifier,
    title: String = "",
    isLoading: Boolean = false,
    onCloseButtonClick: (() -> Unit)? = null,
) {
    AppTheme {
        ConstraintLayout(modifier = modifier.background(color = md_theme_light_onSecondaryContainer)) {
            val (buttonCloseRef, titleRef, progressRef, dividerRef) = createRefs()

            IconButton(
                content = {
                    Icon(
                        imageVector = Icons.Sharp.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }, onClick = {
                    onCloseButtonClick?.invoke()
                }, modifier = Modifier.constrainAs(buttonCloseRef) {
                    start.linkTo(parent.start, 8.dp)
                    centerVerticallyTo(parent)
                })

            Text(
                text = title,
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(titleRef) {
                    start.linkTo(buttonCloseRef.end, 16.dp)
                    end.linkTo(progressRef.start, 4.dp)
                    centerVerticallyTo(parent)
                    width = Dimension.fillToConstraints
                })

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.constrainAs(progressRef) {
                    end.linkTo(parent.end)
                    centerVerticallyTo(parent)
                })
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(dividerRef) {
                        top.linkTo(buttonCloseRef.bottom, 1.dp)
                        bottom.linkTo(parent.bottom)
                    },
                thickness = 1.dp
            )
        }
    }
}

@Preview
@Composable
fun CustomHeaderPreview() {
    CustomHeader(title = "This is title")
}
