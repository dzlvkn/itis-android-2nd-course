package com.example.android2ndcourse.third_task.model

data class NotificationModel (
    val id: Int,
    val notificationTitle: String,
    val notificationText: String?,
    val isChecked: Boolean,
    val importance: NotificationEnum

)