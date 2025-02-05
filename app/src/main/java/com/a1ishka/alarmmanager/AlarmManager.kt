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
    val alarmInfoList = ArrayList<AlarmInfo>()

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
            val alarmInfo = AlarmInfo(pendingIntent, alarmTime)
            alarmInfoList.add(alarmInfo)
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

    fun cancelNearestAlarm(numAlarms: Int) {
        val pendingIntentList = getPendingIntentList(numAlarms)

        val nearestAlarmIndex = findNearestAlarmIndex(pendingIntentList)
        if (nearestAlarmIndex != -1) {
            cancelSelectedAlarm(pendingIntentList[nearestAlarmIndex])
        }
    }

    private fun getPendingIntentList(numAlarms: Int): MutableList<PendingIntent> {
        val pendingIntentList = mutableListOf<PendingIntent>()
        for (i in 0 until numAlarms) {
            val alarmIntent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    i,
                    alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            pendingIntentList.add(pendingIntent)
        }
        return pendingIntentList
    }

    private fun cancelSelectedAlarm(pendingIntent: PendingIntent) {
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "The alarm canceled", Toast.LENGTH_SHORT).show()
    }

    private fun findNearestAlarmIndex(pendingIntentList: MutableList<PendingIntent>): Int {
        val currentTime = System.currentTimeMillis()

        var nearestAlarmIndex = -1
        var smallestTimeDiff = Long.MAX_VALUE

        for (i in 0 until pendingIntentList.size) {
            val pendingIntent = pendingIntentList[i]
            val alarmTime = getAlarmTimeFromPendingIntent(pendingIntent)

            if (alarmTime > currentTime && alarmTime - currentTime < smallestTimeDiff) {
                smallestTimeDiff = alarmTime - currentTime
                nearestAlarmIndex = i
            }
        }

        return nearestAlarmIndex
    }

    private fun getAlarmTimeFromPendingIntent(pendingIntent: PendingIntent): Long {
        for (alarmInfo in alarmInfoList) {
            if (alarmInfo.pendingIntent == pendingIntent) {
                return alarmInfo.alarmTime
            }
        }
        return -1
    }
}