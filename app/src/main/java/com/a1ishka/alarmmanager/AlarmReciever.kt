package com.a1ishka.alarmmanager

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = ContextCompat.getSystemService(context!!, NotificationManager::class.java)

        val builder = NotificationCompat.Builder(context, "alarm_channel")
            .setSmallIcon(R.drawable.ic_expand)
            .setContentTitle("Your alarm!")
            .setContentText("It's time to wake up!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val r = RingtoneManager.getRingtone(context, ringtone)
        r.play()

        notificationManager?.notify(1, builder.build())
    }
}