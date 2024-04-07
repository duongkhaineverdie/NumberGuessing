package com.thoikhongnek.numberguessing.presentation.ui.countdowntime.components

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thoikhongnek.numberguessing.presentation.ui.theme.PurpleGrey80
import com.thoikhongnek.numberguessing.utils.Constants
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime

@Composable
fun CircleCountdown(
    modifier: Modifier = Modifier,
    sizeCircleColor: Dp = 20.dp,
    totalSecond: Long,
    secondRealtime: Long,
    colorHand: Color = PurpleGrey80,
    sizeText: TextUnit = 18.sp
) {
    val degreesCircle = (secondRealtime.toFloat() / totalSecond) * 360
    val textCenter = Constants.secondToTimeCountdown(
        secondRealtime,
        Constants.getDisplayType(totalSecond)
    )
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = CircleShape,
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 5.dp
            ),
        ) {
            val textMeasurer = rememberTextMeasurer()

            val measuredText =
                textMeasurer.measure(
                    text = AnnotatedString(textCenter),
                    style = TextStyle(fontSize = sizeText, fontFamily = FontFamily.Monospace),
                )
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            ) {
                val centerY = size.height / 2
                val centerX = size.width / 2
                val sizeStarted = 5.dp
                clipPath(
                    path = Path(),
                    clipOp = ClipOp.Difference,
                ) {
                    drawArc(
                        brush = Brush.sweepGradient(
                            listOf(
                                Color(0xFFADD8E6), // Xanh da trời pastel
                                Color(0xFFC2F0FF), // Xanh da trời cerulean
                                Color(0xFFFFB7B2), // Cam pastel
                                Color(0xFFFFC0CB), // Hồng phấn
                                Color(0xFFD8BFD8), // Tím pastel
                                Color(0xFFC8A2C8), // Tím lilac
                                Color(0xFFFF7F50), // Cam cháy
                                Color(0xFFADD8E6), // Xanh da trời pastel
                            )
                        ),
                        startAngle = 270f,
                        sweepAngle = degreesCircle,
                        useCenter = true
                    )
                    drawIntoCanvas { canvas ->
                        val paint = Paint()
                        val frameworkPaint = paint.asFrameworkPaint()
                        frameworkPaint.maskFilter =
                            (BlurMaskFilter(10.dp.toPx(), BlurMaskFilter.Blur.NORMAL))
                        frameworkPaint.color = Color.Black.copy(0.4f).toArgb()
                        canvas.drawCircle(
                            center = Offset(centerX, centerY),
                            radius = centerX - (sizeCircleColor - 5.dp).toPx(),
                            paint = paint,
                        )
                    }
                    drawCircle(
                        color = Color.White,
                        radius = centerX - sizeCircleColor.toPx()
                    )

                    drawRoundRect(
                        color = Color.Red,
                        topLeft = Offset(
                            centerX - sizeStarted.toPx() / 2,
                            0f + (sizeCircleColor + 5.dp).toPx()
                        ),
                        size = Size(sizeStarted.toPx(), 10.dp.toPx()),
                        cornerRadius = CornerRadius(200f, 200f)
                    )

                    if (degreesCircle < 360f && degreesCircle > 0f) {
                        rotate(degreesCircle - 180f) {
                            drawRoundRect(
                                color = colorHand,
                                topLeft = Offset(centerX - sizeStarted.toPx() / 2, centerY),
                                size = Size(sizeStarted.toPx(), centerY),
                                cornerRadius = CornerRadius.Zero
                            )
                        }
                    }

                    drawIntoCanvas { canvas ->
                        val paint = Paint()
                        val frameworkPaint = paint.asFrameworkPaint()
                        frameworkPaint.maskFilter =
                            (BlurMaskFilter(20.dp.toPx(), BlurMaskFilter.Blur.NORMAL))
                        frameworkPaint.color = Color.Black.copy(0.4f).toArgb()
                        canvas.drawCircle(
                            center = Offset(centerX, centerY),
                            radius = measuredText.size.width.toFloat() / 2 + 15.dp.toPx(),
                            paint = paint,
                        )
                    }

                    drawCircle(
                        color = Color.White,
                        radius = measuredText.size.width.toFloat() / 2 + 10.dp.toPx()
                    )

                    drawText(
                        measuredText,
                        topLeft = Offset(
                            centerX - measuredText.size.width / 2,
                            centerY - measuredText.size.height / 2
                        )
                    )
                }
            }
        }
    }
}

@Preview(
    name = "CircleCountdown",
    showBackground = true,
    backgroundColor = android.graphics.Color.GRAY.toLong(),
    widthDp = 300,
    heightDp = 300,
)
@Composable
private fun PreviewCircleCountdown() {
    CircleCountdown(
        modifier = Modifier.fillMaxSize(),
        totalSecond = 555,
        secondRealtime = 554
    )
}