package com.a1ishka.alarmmanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlarmListItem(alarmTime: String, isActive: Boolean, onToggleAlarm: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = alarmTime, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = isActive, onCheckedChange = { onToggleAlarm(it) })
    }
}

@Composable
fun AlarmList(alarmList: List<Pair<String, Boolean>>, onToggleAlarm: (Int, Boolean) -> Unit) {
    LazyColumn {
        itemsIndexed(alarmList) { index, (alarmTime, isActive) ->
            if (isActive) {
                AlarmListItem(
                    alarmTime = alarmTime,
                    isActive = isActive,
                    onToggleAlarm = { onToggleAlarm(index, it) }
                )
            }
        }
    }
}