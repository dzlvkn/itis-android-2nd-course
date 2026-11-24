package com.example.android2ndcourse.classwork2

import kotlinx.coroutines.delay

class CoroutineRepository {

    suspend fun sampleFun() {
        delay(5000L)
    }
}