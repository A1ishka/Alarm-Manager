package com.a1ishka.alarmmanager

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.a1ishka.alarmmanager.ui.theme.AlarmManagerTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LaunchedEffect(true) {
                createNotificationChannel(this@MainActivity)

                val permissions = arrayOf(
                    Manifest.permission.VIBRATE,
                    Manifest.permission.MODIFY_AUDIO_SETTINGS
                )
                val requestCode = 789

                if (ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.VIBRATE
                    ) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this@MainActivity, permissions, requestCode)
                }
            }

            AlarmManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AlarmApp(this@MainActivity)
                }
            }
        }
    }
}