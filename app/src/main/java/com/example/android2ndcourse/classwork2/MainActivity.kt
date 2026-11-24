package com.example.android2ndcourse.classwork2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {

    private val customScope =
        CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Screen(customScope)
        }
    }

    override fun onDestroy() {
        customScope.cancel()
        super.onDestroy()
    }
}
