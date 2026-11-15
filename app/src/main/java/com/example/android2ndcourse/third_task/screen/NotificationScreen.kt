package com.example.android2ndcourse.third_task.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.android2ndcourse.R
import com.example.android2ndcourse.second_task.Consts
import com.example.android2ndcourse.third_task.model.NotificationEnum
import com.example.android2ndcourse.third_task.model.NotificationModel

@Composable
fun NotificationScreen(
    onSendNotification: (NotificationModel) -> Unit,
    editNotification: (NotificationModel) -> Unit,
    deleteAllNotifications: () -> Unit,
    hasActiveNotificationState: Boolean,
    toLocationScreen: () -> Unit,
    cntNotificationActive: (Int) -> Boolean
) {

    var notificationTitle by remember { mutableStateOf("") }
    var notificationText by remember { mutableStateOf("") }

    var isChecked by remember { mutableStateOf(false) }

    var isExpanded by remember { mutableStateOf(false) }
    var importance by remember {mutableStateOf(NotificationEnum.DEFAULT)}

    var currentId by rememberSaveable() { mutableStateOf(0) }

    var notificationIdForRedacting by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    start = Consts.DEFAULT_PADDING_DP,
                    end = Consts.DEFAULT_PADDING_DP
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.notification),
                fontSize = 32.sp,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            TextField(
                value = notificationTitle,
                onValueChange = {input ->
                    notificationTitle = input
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = stringResource(R.string.notification_title))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            TextField(
                value = notificationText,
                onValueChange = {input ->
                    notificationText = input
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = stringResource(R.string.notification_text))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {input ->
                        isChecked = input
                    }
                )

                Spacer(modifier = Modifier.width(Consts.DEFAULT_PADDING_FOR_NEIGHBORING_ELEMENTS))

                Text(
                    text = stringResource(R.string.checkbox_text)
                )
            }

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            Text(
                text = stringResource(R.string.dropdown_menu_text)
            )

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_FOR_NEIGHBORING_ELEMENTS))

            Box(
                contentAlignment = Alignment.Center
            ) {
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = {isExpanded = false}

                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(R.string.high_importance))
                        },
                        onClick = {
                            importance = NotificationEnum.HIGH
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(R.string.default_importance))
                        },
                        onClick = {
                            importance = NotificationEnum.DEFAULT
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(R.string.min_importance))
                        },
                        onClick = {
                            importance = NotificationEnum.MIN
                            isExpanded = false
                        }
                    )
                }

                Button(
                    onClick = {isExpanded = true}
                ) {
                    Text(text =
                        when(importance) {
                            NotificationEnum.MIN -> stringResource(R.string.min_importance)
                            NotificationEnum.HIGH -> stringResource(R.string.high_importance)
                            else -> stringResource(R.string.default_importance)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            Button(
                onClick = {
                    currentId += 1

                    while (cntNotificationActive(currentId)) {
                        currentId += 1
                    }
                    val notification = NotificationModel(
                        currentId,
                        notificationTitle,
                        notificationText,
                        isChecked,
                        importance
                    )
                    onSendNotification(notification)
                    notificationTitle = ""
                    notificationText = ""
                    isChecked = false
                    importance = NotificationEnum.DEFAULT
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.button_send))
            }

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            Text(
                text = stringResource(R.string.redacting_notification),
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            TextField(
                value = notificationIdForRedacting,
                onValueChange = {input ->
                    notificationIdForRedacting = input
                },
                label = {
                    Text(
                        text = stringResource(R.string.redacting_text_field)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                enabled = hasActiveNotificationState
            )

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            Button(
                onClick = {
                    val id = notificationIdForRedacting.toIntOrNull()
                    if (id != null) {
                        val newModel = NotificationModel(
                            id,
                            notificationTitle,
                            notificationText,
                            isChecked,
                            importance
                        )
                        editNotification(newModel)
                    }
                    notificationTitle = ""
                    notificationText = ""
                    notificationIdForRedacting = ""
                },
                enabled = hasActiveNotificationState
            ) {
                Text(
                    text = stringResource(R.string.button_redacting)
                )
            }

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            Button(
                onClick = {
                    deleteAllNotifications()
                    notificationTitle = ""
                    notificationText = ""
                    notificationIdForRedacting = ""
                },
                enabled = hasActiveNotificationState
            ) {
                Text(
                    text = stringResource(R.string.button_delete_all)
                )
            }

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            Button(
                onClick = {
                    toLocationScreen()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.button_move_to_location_screen)
                )
            }

        }
    }
}