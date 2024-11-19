package com.kirill.notifyapplication.presentation

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.kirill.notifyapplication.R
import com.kirill.notifyapplication.presentation.alarm.AlarmObject
import com.kirill.notifyapplication.presentation.alarm.NotifyReceiver
import com.kirill.notifyapplication.presentation.navigation.SetupNavGraph
import com.kirill.notifyapplication.presentation.theme.NotifyApplicationTheme
import com.kirill.notifyapplication.presentation.viewmodel.NotifyViewModel
import kotlinx.datetime.LocalTime
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {

    private val notifyViewModel by viewModel<NotifyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AlarmObject.alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        super.onCreate(savedInstanceState)
        if (!NotifyReceiver.initManager(applicationContext)) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.enable_notifications))
                .setPositiveButton("OK") { _, _ ->
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    startActivity(intent)
                }
                .create()
                .show()
        }
        setContent {
            NotifyApplicationTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController, notifyViewModel, this)
            }
        }
    }

}