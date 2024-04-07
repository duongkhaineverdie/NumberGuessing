package com.thoikhongnek.numberguessing.data.model

data class MinuteAndHour(
    val minute: Float,
    val hour: Float,
    val second: Float,
)

data class DateTimeHomeDisplay(
    val minute: String,
    val hour: String,
    val date: String,
    val monthString: String,
    val typeTime: String,
)
