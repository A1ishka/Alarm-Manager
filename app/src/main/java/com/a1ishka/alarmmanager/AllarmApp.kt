package com.a1ishka.alarmmanager

import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmApp(context: Context) {
    val startTime = remember { Calendar.getInstance() }
    var time by remember { mutableStateOf("") }
    var numAlarms by remember { mutableStateOf("") }
    var interval by remember { mutableStateOf("") }

    val alarmList = remember { mutableStateListOf<Pair<String, Boolean>>() }
    val timeFormatter = remember {
        SimpleDateFormat("HH:mm", Locale.getDefault())
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        ShowTimePicker(
            context,
            startTime.get(Calendar.HOUR_OF_DAY),
            startTime.get(Calendar.MINUTE)
        ) { selectedTime ->
            time = selectedTime
        }

        OutlinedTextField(
            value = numAlarms,
            onValueChange = { numAlarms = it },
            label = { Text("Alarm Count") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        OutlinedTextField(
            value = interval,
            onValueChange = { interval = it },
            label = { Text("Gap (minutes)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Button(
            onClick = {
                val count = numAlarms.toIntOrNull() ?: 0
                val gap = interval.toIntOrNull() ?: 0

                if (count > 0 && gap > 0 && time.isNotEmpty()) {
                    val baseTime = timeFormatter.parse(time)?.time ?: return@Button
                    alarmList.clear()

                    for (i in 0 until count) {
                        val alarmTime = baseTime + i * gap * 60 * 1000
                        val formattedTime = timeFormatter.format(Date(alarmTime))
                        alarmList.add(formattedTime to true)
                    }
                    AlarmManager.scheduleAlarm(timeFormatter.format(Date(baseTime)), count, gap, context)
                }
            }
        ) {
            Text("Set alarms")
        }

        Button(
            onClick = {
                AlarmManager.cancelAllAlarms(alarmList.size)
                alarmList.clear()
            }
        ) {
            Text("Cancel all")
        }

        AlarmList(
            alarmList = alarmList,
            onToggleAlarm = { index, isActive ->
                alarmList[index] = alarmList[index].copy(second = isActive)
                if (!isActive) { /*Отменяем конкретный будильник*/    }
            }
        )
    }
}