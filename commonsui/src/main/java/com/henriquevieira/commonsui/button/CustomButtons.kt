package com.henriquevieira.commonsui.button

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomCircleIconButton(
    imageVector: ImageVector,
    imageColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentDescription: String = "",
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape),
        onClick = {
            onClick()
        },
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = imageColor,
            containerColor = backgroundColor
        )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(35.dp)
        )
    }
}