package com.kirill.notifyapplication.presentation.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.kirill.notifyapplication.domain.Notify
import com.kirill.notifyapplication.domain.NotifyRepository
import com.kirill.notifyapplication.presentation.Keys
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject

class NotifyReceiver : BroadcastReceiver() {

    private val repository : NotifyRepository by inject(NotifyRepository::class.java)

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            repository.getNotifyList()
                .subscribeOn(Schedulers.newThread())
                .subscribe(object : SingleObserver<List<Notify>> {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onSuccess(t: List<Notify>) {
                        t.forEach {
                            AlarmObject.createRepeating(
                                it.id,
                                it.time,
                                it.name,
                                it.text,
                                context
                            )
                        }
                    }

                    override fun onError(e: Throwable) {}
                })
        }
        else {
            val name = intent?.getStringExtra(Keys.NAME_KEY).toString()
            val text = intent?.getStringExtra(Keys.TEXT_KEY).toString()
            val id = intent?.getIntExtra(Keys.ID_KEY, 0)!!
            val notification = NotifyBuilder(name, text).createNotify(context)
            val tempManager = NotificationManagerCompat.from(context)
            if (tempManager.areNotificationsEnabled()) {
                tempManager.notify(id, notification)
            }
        }
    }

    companion object {
        private var notificationManager : NotificationManager? = null

        fun initManager(context: Context) : Boolean {
            if (notificationManager == null) {
                notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                NotifyBuilder.initChannel()
                notificationManager?.createNotificationChannel(NotifyBuilder.channel!!)
                return notificationManager?.areNotificationsEnabled()!!
            }
            return true
        }

    }
}