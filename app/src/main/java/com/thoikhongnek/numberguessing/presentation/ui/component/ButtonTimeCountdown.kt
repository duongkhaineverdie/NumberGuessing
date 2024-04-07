package com.thoikhongnek.numberguessing.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thoikhongnek.numberguessing.utils.innerShadow
import com.thoikhongnek.numberguessing.utils.shadow

@Composable
fun ButtonTimeCountdown(
    onClick: () -> Unit,
    color: Color = Color.White.copy(0.4f),
    shape: Shape = CircleShape,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .shadow(
                color = Color.White,
                offsetX = (-8).dp,
                offsetY = (-8).dp,
                blurRadius = 10.dp,
                sizeMore = 5.dp
            )
            .shadow(
                color = Color.Black.copy(alpha = 0.3f),
                offsetX = (4).dp,
                offsetY = (4).dp,
                blurRadius = 10.dp,
                sizeMore = 5.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(shape)
                .clickable {
                    onClick()
                }
                .background(color)
                .innerShadow(
                    shape = shape,
                    offsetX = 2.dp,
                    offsetY = 2.dp,
                    color = Color.Black.copy(0.5f),
                    blur = 10.dp
                )
                .innerShadow(
                    shape = shape,
                    offsetY = (-2).dp,
                    offsetX = (-2).dp,
                    color = Color.White.copy(0.8f),
                    blur = 10.dp
                ),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}