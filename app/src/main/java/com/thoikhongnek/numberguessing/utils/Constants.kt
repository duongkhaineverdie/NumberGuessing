package com.thoikhongnek.numberguessing.utils

import com.thoikhongnek.numberguessing.data.model.DateTimeHomeDisplay
import com.thoikhongnek.numberguessing.data.model.MinuteAndHour
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Constants {
    fun convertMillisToFormattedDate(timestamp: Long): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm aaa", Locale.US)
        val date = Date(timestamp)
        return formatter.format(date)
    }

    fun convertMillisToMinuteAndHour(timestamp: Long): MinuteAndHour {
        val localDateTime = Instant.fromEpochMilliseconds(timestamp).toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
        val minute = localDateTime.minute + localDateTime.second / 60f
        val hour = localDateTime.hour + localDateTime.minute / 60f
        val second = localDateTime.second
        return MinuteAndHour(minute, hour, second.toFloat())
    }


    fun convertMillisToDateHome(timestamp: Long): DateTimeHomeDisplay {
        val simpleDateFormat = SimpleDateFormat("a MMM dd HH:mm", Locale.US)
        val formattedDate = simpleDateFormat.format(Date(timestamp))

        val amPm = formattedDate.substring(0, 2)
        val month = formattedDate.substring(3, 6)
        val day = formattedDate.substring(7, 9)
        val hour = formattedDate.substring(10, 12)
        val minute = formattedDate.substring(13, 15)

        return DateTimeHomeDisplay(minute = minute, hour = hour, date = day, monthString = month, typeTime = amPm)
    }

    fun convertMinuteToDegrees(minute: Float): Float {
        val result = ((minute % 60) / 60) * 360 - 180
        return result
    }

    fun convertHourToDegrees(hour: Float): Float {
        val result = ((hour % 12) / 12) * 360 - 180
        return result
    }

    fun getDisplayType(totalSecond: Long): Int {
        return when {
            totalSecond <= 59 -> 1
            totalSecond <= 3599 -> 2
            else -> 3
        }
    }

    fun secondToTimeCountdown(seconds: Long, displayType: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secondsRemaining = (seconds % 3600) % 60

        val formattedTime = when (displayType) {
            3 -> "${hours.format(2)}:${minutes.format(2)}:${secondsRemaining.format(2)}"
            2 -> "${minutes.format(2)}:${secondsRemaining.format(2)}"
            1 -> secondsRemaining.format(2)
            else -> throw IllegalArgumentException("Invalid display type: $displayType")
        }

        return formattedTime
    }
}

