package com.kirill.notifyapplication

import android.app.Application
import android.content.Intent
import com.kirill.notifyapplication.di.appModule
import com.kirill.notifyapplication.di.dataModule
import com.kirill.notifyapplication.presentation.alarm.NotifyReceiver
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val intent = Intent(applicationContext, NotifyReceiver::class.java)
        applicationContext.startForegroundService(intent)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(dataModule, appModule))
        }
    }
}