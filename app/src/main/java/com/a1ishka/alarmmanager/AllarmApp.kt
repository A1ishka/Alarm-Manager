package com.a1ishka.alarmmanager

import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmApp(context: Context) {
    val startTime by remember { mutableStateOf(Calendar.getInstance()) }
    var time by remember { mutableStateOf("") }
    var numAlarms by remember { mutableStateOf("") }
    var interval by remember { mutableStateOf("") }

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
            keyboardActions = KeyboardActions(onDone = { /*TODO*/ }),
            singleLine = true
        )

        OutlinedTextField(
            value = interval,
            onValueChange = { interval = it },
            label = { Text("Gap (minutes)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = { /*TODO*/ }),
            singleLine = true
        )

        Button(
            onClick = { AlarmManager.scheduleAlarm(time, numAlarms.toInt(), interval.toInt(), context)  }
        ) {
            Text("Set alarms")
        }

        Button(
            onClick = { AlarmManager.cancelAllAlarms(numAlarms.toInt()) }
        ) {
            Text("Cancel all")
        }

        Button(
            onClick = { AlarmManager.cancelNearestAlarm() }
        ) {
            Text("Cancel nearest")
        }
    }
}