package com.example.android2ndcourse.third_task

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import com.example.android2ndcourse.third_task.screen.NotificationPermissionScreen
import com.example.android2ndcourse.third_task.screen.NotificationScreen
import com.example.android2ndcourse.third_task.service.NotificationHandler
import android.provider.Settings
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android2ndcourse.third_task.screen.LocationScreen

class MainActivity: ComponentActivity() {

    private var hasNotificationPermission by mutableStateOf(false)
    private val notificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {result ->
        if (result) {
            hasNotificationPermission = true
        } else {
            hasNotificationPermission = false
        }
    }

    private var hasActiveNotifications by mutableStateOf(false)

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val notificationText = intent.getStringExtra(Consts.NOTIFICATION_TEXT)
        if (notificationText != null) {
            Toast.makeText(this, notificationText, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notificationTitle = intent.getStringExtra(Consts.NOTIFICATION_TITLE)
        if (notificationTitle != null) {
            Toast.makeText(this, notificationTitle, Toast.LENGTH_SHORT).show()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                hasNotificationPermission = true
            } else {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            hasNotificationPermission = true
        }

        val notificationHandler = NotificationHandler(this)
        notificationHandler.createNotificationChannels()


        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            if (hasNotificationPermission) {
                NavHost(
                    navController = navController,
                    startDestination = "notifications"
                ) {
                    composable("location") {
                        LocationScreen(this@MainActivity)
                    }

                    composable("notifications") {
                        NotificationScreen(
                            { model ->
                                notificationHandler.sendNotification(model)
                                hasActiveNotifications = notificationHandler.hasActiveNotification()
                            },
                            { model ->
                                if (notificationHandler.isNotificationActive(model.id)) {
                                    notificationHandler.sendNotification(model)
                                }
                            },
                            {
                                notificationHandler.deleteAllNotifications()
                                hasActiveNotifications = notificationHandler.hasActiveNotification()
                            },
                            hasActiveNotifications,
                            {navController.navigate("location")},
                            {id -> notificationHandler.isNotificationActive(id)}
                        )
                    }
                }
            } else {
                NotificationPermissionScreen({
                    val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    settingsIntent.data = Uri.parse("package:$packageName")
                    startActivity((settingsIntent))
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                hasNotificationPermission = true
            } else {
                hasNotificationPermission = false
            }
        } else {
            hasNotificationPermission = true
        }

        val notificationHandler = NotificationHandler(this)
        hasActiveNotifications = notificationHandler.hasActiveNotification()
    }
}