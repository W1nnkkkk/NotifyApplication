package com.kirill.notifyapplication.presentation.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.kirill.notifyapplication.R

class NotifyBuilder(
    val name : String,
    val text : String
) {

    fun createNotify(context: Context) : Notification{
        return NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setContentTitle(name)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_notification)
            .build()
    }

    companion object {
        private val CHANNEL_ID = "default"
        private val CHANNEL_NAME = "base channel for notify"
        var channel : NotificationChannel? = null
            set(value) {
                if (field == null) {
                    field = value
                }
            }

        fun initChannel() {
            channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
        }
    }
}