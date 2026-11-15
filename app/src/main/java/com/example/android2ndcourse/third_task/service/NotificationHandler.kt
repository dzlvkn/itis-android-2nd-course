package com.example.android2ndcourse.third_task.service


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.android2ndcourse.R
import com.example.android2ndcourse.third_task.Consts
import com.example.android2ndcourse.third_task.MainActivity
import com.example.android2ndcourse.third_task.model.NotificationEnum
import com.example.android2ndcourse.third_task.model.NotificationModel

class NotificationHandler(
    private val ctx: Context,
) {

    private val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun createNotificationChannels() {
        notificationManager.createNotificationChannels(Consts.notificationChannels)
    }

    fun sendNotification(model: NotificationModel) {
        val channel = when (model.importance) {
            NotificationEnum.HIGH -> {
                Consts.notificationChannels.first().id
            }

            NotificationEnum.DEFAULT -> {
                Consts.notificationChannels[1].id
            }

            NotificationEnum.MIN -> {
                Consts.notificationChannels.last().id
            }
        }

        val intent = Intent(ctx, MainActivity::class.java)
            .putExtra(Consts.NOTIFICATION_TITLE, model.notificationTitle)
            .putExtra(Consts.NOTIFICATION_TEXT, model.notificationText)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(ctx, model.id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(ctx, channel)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(model.notificationTitle)
            .setContentText(model.notificationText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(model.notificationText))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(model.isChecked)
            .setContentIntent(pendingIntent)

        notificationManager.notify(model.id, builder.build())

    }

    fun hasActiveNotification(): Boolean {
        return notificationManager.activeNotifications.isNotEmpty()
    }

    fun isNotificationActive(notificationId: Int): Boolean {
        return notificationManager.activeNotifications.any{notification -> notification.id == notificationId}
    }

    fun deleteAllNotifications() {
        notificationManager.cancelAll()
    }

}