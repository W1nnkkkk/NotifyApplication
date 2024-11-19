package com.kirill.notifyapplication.presentation

import android.icu.util.Calendar
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toLocalTime

object TimeObject {
    fun getTimeNow() : LocalTime {
        return Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()).time
    }

    fun getMillisFromTime(localTime: LocalTime) : Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, localTime.hour)
        calendar.set(Calendar.MINUTE, localTime.minute)
        return calendar.timeInMillis
    }
}