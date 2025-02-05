package com.a1ishka.alarmmanager

import android.app.PendingIntent


data class AlarmInfo(val pendingIntent: PendingIntent, val alarmTime: Long)