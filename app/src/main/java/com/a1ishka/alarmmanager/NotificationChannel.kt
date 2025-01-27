package com.a1ishka.alarmmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager

fun createNotificationChannel(context: Context) {
    val channelId = "alarm_channel"
    val channelName = "Alarm Default Channel"
    val channelDescription = "This is the default channel for notifications of the alarm app"
    val importance = NotificationManager.IMPORTANCE_DEFAULT

    val channel = NotificationChannel(channelId, channelName, importance).apply {
        description = channelDescription
        enableVibration(true)
        vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
        enableLights(true)
        lightColor = Color.RED
        setSound(
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
        )
    }

    val notificationManager = context.getSystemService(NotificationManager::class.java)
    notificationManager.createNotificationChannel(channel)
}