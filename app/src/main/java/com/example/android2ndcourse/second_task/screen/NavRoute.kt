package com.example.android2ndcourse.second_task.screen

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute {

    data object Login: NavRoute()

    data object Result: NavRoute()
}