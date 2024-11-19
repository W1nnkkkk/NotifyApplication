package com.kirill.notifyapplication.presentation.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.kirill.notifyapplication.presentation.Keys
import com.kirill.notifyapplication.presentation.TimeObject
import kotlinx.datetime.LocalTime

object AlarmObject {
    var alarmManager :  AlarmManager? = null
        set(value) {
            if (field == null) {
                field = value
            }
        }

    fun createRepeating(
        id : Int,
        localTime: LocalTime,
        name : String,
        text : String,
        context: Context
    ) {
        val alarmIntent = Intent(context, NotifyReceiver::class.java).let {
            it.putExtra(Keys.NAME_KEY, name)
            it.putExtra(Keys.TEXT_KEY, text)
            it.putExtra(Keys.ID_KEY, id)
            PendingIntent.getBroadcast(context,
                id, it, PendingIntent.FLAG_IMMUTABLE)
        }

        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            TimeObject.getMillisFromTime(localTime),
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
    }

    fun cancelRepeating(id : Int, context: Context) {
        val alarmIntent = Intent(context, NotifyReceiver::class.java).let {
            PendingIntent.getBroadcast(context,
                id, it, PendingIntent.FLAG_IMMUTABLE)
        }

        alarmManager?.cancel(alarmIntent)
    }

}