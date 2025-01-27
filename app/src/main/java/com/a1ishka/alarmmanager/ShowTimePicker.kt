package com.a1ishka.alarmmanager

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ShowTimePicker(
    context: Context,
    initHour: Int,
    initMinute: Int,
    onTimeSelected: (String) -> Unit
) {
    val time = remember { mutableStateOf("") }
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            val selectedTime = "$hour:$minute"
            time.value = selectedTime
            onTimeSelected(selectedTime)
        }, initHour, initMinute, false
    )
    Button(onClick = {
        timePickerDialog.show()
    }) {
        Text(text = "Open Time Picker")
    }
}