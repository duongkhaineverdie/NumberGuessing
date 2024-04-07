package com.thoikhongnek.numberguessing.presentation.ui.numberguessinggame.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.isUnspecified

@Composable
fun AutoTextSize(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    color: Color = style.color
) {
    var textStyle by remember { mutableStateOf(style) }
    var readyToDraw by remember { mutableStateOf(false) }
    val defaultFontSize = style.fontSize

    Text(
        text = text,
        color = color,
        modifier = modifier.drawWithContent {
            if (readyToDraw) {
                drawContent()
            }
        },
        softWrap = false,
        style = textStyle,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                if (style.fontSize.isUnspecified) {
                    textStyle = textStyle.copy(
                        fontSize = defaultFontSize
                    )
                }
                textStyle = textStyle.copy(
                    fontSize = textStyle.fontSize * 0.95
                )
            } else {
                readyToDraw = true
            }
        })
}

@Preview(name = "AutoTextSize")
@Composable
private fun PreviewAutoTextSize() {
    AutoTextSize(
        text = "100"
    )
}