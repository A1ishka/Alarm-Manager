package com.a1ishka.alarmmanager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.util.Calendar

@SuppressLint("StaticFieldLeak")
object AlarmManager {
    private lateinit var context: Context
    private lateinit var alarmManager: AlarmManager

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleAlarm(time: String, numAlarms: Int, intervalMinutes: Int, context: Context) {
        val calendar = Calendar.getInstance()
        this.context = context
        val timeInMillis = convertTimeToMillis(time)
        calendar.timeInMillis = timeInMillis

        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        for (i in 0 until numAlarms) {
            val alarmTime = calendar.timeInMillis + i * intervalMinutes * 60 * 1000

            val alarmIntent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    i,
                    alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
        }

        Toast.makeText(context, "Alarms set", Toast.LENGTH_SHORT).show()
    }

    fun cancelAllAlarms(numAlarms: Int) {
        for (i in 0 until numAlarms) {
            val alarmIntent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent1 =
                PendingIntent.getBroadcast(
                    context,
                    i,
                    alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

            alarmManager.cancel(pendingIntent1)
        }
        Toast.makeText(context, "Alarms canceled", Toast.LENGTH_SHORT).show()
    }

    fun cancelNearestAlarm() {
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "The alarm canceled", Toast.LENGTH_SHORT).show()
    }
}