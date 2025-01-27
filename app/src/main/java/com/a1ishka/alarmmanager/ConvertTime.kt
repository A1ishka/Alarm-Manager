package com.a1ishka.alarmmanager

import java.util.Calendar

fun convertTimeToMillis(timeString: String): Long {
    val timeParts = timeString.split(":")
    if (timeParts.size != 2) {
        return -1
    }

    val hours = timeParts[0].toIntOrNull() ?: 0
    val minutes = timeParts[1].toIntOrNull() ?: 0

    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hours)
    calendar.set(Calendar.MINUTE, minutes)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    return calendar.timeInMillis
}