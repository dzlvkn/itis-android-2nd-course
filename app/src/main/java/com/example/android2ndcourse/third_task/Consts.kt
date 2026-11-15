package com.example.android2ndcourse.third_task

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

object Consts {

    @RequiresApi(Build.VERSION_CODES.O)
    val notificationChannels = listOf(
        NotificationChannel(
            "high_importance_channel",
            "Важные уведомления",
            NotificationManager.IMPORTANCE_HIGH
        ),
        NotificationChannel(
            "default_importance_channel",
            "Уведомления средней важности",
            NotificationManager.IMPORTANCE_DEFAULT
        ),
        NotificationChannel(
            "min_importance_channel",
            "Неважные уведомления",
            NotificationManager.IMPORTANCE_MIN
        )
    )

    const val NOTIFICATION_TITLE = "notification_title"
    const val NOTIFICATION_TEXT = "notification_text"
}