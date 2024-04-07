package com.thoikhongnek.numberguessing.presentation.ui.numberguessinggame.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thoikhongnek.numberguessing.utils.TypeButton

@Composable
fun ButtonNumber(
    modifier: Modifier = Modifier,
    number: Int,
    typeButton: TypeButton = TypeButton.ENABLE,
    border: BorderStroke = BorderStroke(1.dp, Color.Black),
    onClick: () -> Unit
) {
    val colorButton = when (typeButton) {
        TypeButton.CORRECT -> Color.Green
        TypeButton.WRONG -> Color.Red
        TypeButton.DISABLE -> Color.Gray
        TypeButton.ENABLE -> Color.White
    }
    Button(
        modifier = modifier
            .aspectRatio(1f),
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorButton,
            contentColor = Color.Black
        ),
        border = border,
        contentPadding = PaddingValues(0.dp),
        enabled = typeButton != TypeButton.DISABLE
    ) {
        Text(text = number.toString(), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Preview(
    name = "ButtonNumber",
    widthDp = 300,
    heightDp = 400,
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun PreviewButtonNumber() {
    ButtonNumber(
        modifier = Modifier.fillMaxSize(),
        number = 1,
        typeButton = TypeButton.ENABLE,
        onClick = {/* no-op */ }
    )
}