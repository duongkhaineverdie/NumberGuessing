package com.thoikhongnek.numberguessing.presentation.ui.component

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thoikhongnek.numberguessing.utils.Constants
import com.thoikhongnek.numberguessing.utils.innerShadow
import com.thoikhongnek.numberguessing.utils.shadow
import kotlinx.datetime.Clock
import kotlin.math.abs

@Composable
fun DigitalClock(
    modifier: Modifier = Modifier,
    sizeHand: Dp = 10.dp,
    colorHand: Color = MaterialTheme.colorScheme.primary,
    paddingLengthHand: Dp = 40.dp,
    timeStamp: Long,
    sizeMoreOutline: Dp = 30.dp,
    sizeHandSecond: Dp = 2.dp
) {
    val minuteAndHour = Constants.convertMillisToMinuteAndHour(timeStamp)
    Box(modifier, contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shadow(
                    color = Color.White.copy(alpha = 0.6f),
                    offsetX = (-8).dp,
                    offsetY = (-8).dp,
                    blurRadius = 15.dp,
                    sizeMore = sizeMoreOutline
                )
                .shadow(
                    color = Color.Black.copy(alpha = 0.6f),
                    offsetX = (8).dp,
                    offsetY = (8).dp,
                    blurRadius = 15.dp,
                    sizeMore = sizeMoreOutline
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        offsetX = (-8).dp,
                        offsetY = (-8).dp,
                        blurRadius = 15.dp,
                        sizeMore = 20.dp
                    )
                    .shadow(
                        color = Color.White.copy(alpha = 0.3f),
                        offsetX = (8).dp,
                        offsetY = (8).dp,
                        blurRadius = 15.dp,
                        sizeMore = 20.dp
                    )
            )
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerY = size.height / 2
            val centerX = size.width / 2
            val offsetCenter = Offset(centerX, centerY)
            val lengthHandMinute = centerY - paddingLengthHand.toPx()

            val degreesHourHand = Constants.convertHourToDegrees(minuteAndHour.hour)
            val constNumberHourHand = (90 - (abs(degreesHourHand) % 360)) / 90

            val degreesMinuteHand = Constants.convertMinuteToDegrees(minuteAndHour.minute)
            val constNumberMinuteHand = (90 - (abs(degreesMinuteHand) % 360)) / 90

            val degreesSecondHand = Constants.convertMinuteToDegrees(minuteAndHour.second)
            val constNumberSecondHand = (90 - (abs(degreesSecondHand) % 360)) / 90

            rotate(degreesHourHand) {
                drawIntoCanvas { canvas ->
                    val paint = Paint()
                    val frameworkPaint = paint.asFrameworkPaint()
                    frameworkPaint.maskFilter =
                        (BlurMaskFilter(10.dp.toPx(), BlurMaskFilter.Blur.NORMAL))
                    frameworkPaint.color = Color.Black.copy(0.5f).toArgb()
                    canvas.drawRoundRect(
                        left = centerX,
                        top = centerY,
                        right = centerX + constNumberHourHand * sizeHand.toPx(),
                        bottom = (size.height) * 3 / 4 - paddingLengthHand.toPx() / 2,
                        paint = paint,
                        radiusX = 50f,
                        radiusY = 50f
                    )
                }

            }

            rotate(degreesMinuteHand) {
                drawIntoCanvas { canvas ->
                    val paint = Paint()
                    val frameworkPaint = paint.asFrameworkPaint()
                    frameworkPaint.maskFilter =
                        (BlurMaskFilter(10.dp.toPx(), BlurMaskFilter.Blur.NORMAL))
                    frameworkPaint.color = Color.Black.copy(0.5f).toArgb()
                    canvas.drawRoundRect(
                        left = centerX,
                        top = centerY,
                        right = centerX + constNumberMinuteHand * sizeHand.toPx(),
                        bottom = size.height - paddingLengthHand.toPx(),
                        paint = paint,
                        radiusX = 50f,
                        radiusY = 50f
                    )
                }

            }

            rotate(degreesSecondHand) {
                drawIntoCanvas { canvas ->
                    val paint = Paint()
                    val frameworkPaint = paint.asFrameworkPaint()
                    frameworkPaint.maskFilter =
                        (BlurMaskFilter(10.dp.toPx(), BlurMaskFilter.Blur.NORMAL))
                    frameworkPaint.color = Color.Black.copy(0.5f).toArgb()
                    canvas.drawRoundRect(
                        left = centerX,
                        top = centerY,
                        right = centerX + constNumberSecondHand * sizeHandSecond.toPx(),
                        bottom = size.height - paddingLengthHand.toPx(),
                        paint = paint,
                        radiusX = 50f,
                        radiusY = 50f
                    )
                }
            }

            rotate(degrees = degreesHourHand) {
                drawRoundRect(
                    color = colorHand,
                    topLeft = Offset(
                        centerX - sizeHand.toPx() / 2,
                        centerY + 5.dp.toPx()
                    ),
                    cornerRadius = CornerRadius(200f, 200f),
                    size = Size(sizeHand.toPx(), lengthHandMinute / 2),
                )
            }



            rotate(degrees = degreesMinuteHand) {
                drawRoundRect(
                    color = colorHand,
                    topLeft = Offset(
                        centerX - sizeHand.toPx() / 2,
                        centerY + 5.dp.toPx()
                    ),
                    cornerRadius = CornerRadius(200f, 200f),
                    size = Size(sizeHand.toPx(), lengthHandMinute),
                )
            }



            rotate(degrees = degreesSecondHand) {
                drawRoundRect(
                    color = Color.Red,
                    topLeft = Offset(
                        centerX - sizeHandSecond.toPx() / 2,
                        centerY - 10.dp.toPx()
                    ),
                    cornerRadius = CornerRadius(200f, 200f),
                    size = Size(sizeHandSecond.toPx(), lengthHandMinute + 10.dp.toPx()),
                )
            }
        }
    }
}

@Preview(
    name = "DigitalClock",
    showBackground = true,
    backgroundColor = 0xFF41444D,
    widthDp = 300,
    heightDp = 300
)
@Composable
private fun PreviewDigitalClock() {
    DigitalClock(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        timeStamp = Clock.System.now().toEpochMilliseconds(),
        paddingLengthHand = 30.dp,
    )
}