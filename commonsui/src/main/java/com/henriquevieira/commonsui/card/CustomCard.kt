package com.henriquevieira.commonsui.card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    contentColor: Color,
    onClick: (() -> Unit)? = null,
    onLongPress: (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(horizontal = 16.dp)
            .combinedClickable(
                enabled = true,
                onClick = {
                    onClick?.invoke()
                },
                onLongClick = {
                    onLongPress?.invoke()
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
        ) {
            content?.invoke()
        }
    }
}