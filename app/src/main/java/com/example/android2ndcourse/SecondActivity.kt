package com.example.android2ndcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val result = intent.getStringExtra("result") ?: ""
            SecondScreen(result)
        }
    }
}

@Composable
fun SecondScreen(result: String) {
    Text(
        result,
        modifier = Modifier.padding(
                    start = 16.dp,
                    top = 64.dp
                )
    )
}


