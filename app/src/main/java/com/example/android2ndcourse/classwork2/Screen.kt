package com.example.android2ndcourse.classwork2


import android.widget.SeekBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.android2ndcourse.R
import com.example.android2ndcourse.second_task.Consts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.*

@Composable
fun Screen(customScope: CoroutineScope) {

    val repository = remember { CoroutineRepository() }

    var count by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var job by remember { mutableStateOf<Job?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.coroutines),
            fontSize = 32.sp
        )

        Spacer(modifier =  Modifier.height(Consts.DEFAULT_PADDING_DP))

        Text(
            text = "${stringResource(R.string.count_coroutines)} $count",
            fontSize = 24.sp
        )

        Spacer(modifier =  Modifier.height(Consts.DEFAULT_PADDING_DP))

        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = {
                SeekBar(it).apply {
                    max = 10

                    setOnSeekBarChangeListener(
                        object : SeekBar.OnSeekBarChangeListener {

                            override fun onProgressChanged(
                                seekBar: SeekBar?,
                                progress: Int,
                                fromUser: Boolean
                            ) {
                                count = progress
                            }

                            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                        }
                    )
                }
            },
            update = {
                it.isEnabled = !isRunning
            }
        )

        Spacer(modifier =  Modifier.height(Consts.DEFAULT_PADDING_DP))

        Button(
            enabled = !isRunning && count > 0,
            onClick = {
                job = customScope.launch {
                    isRunning = true

                    try {
                        repeat(count) {
                            async {
                                repository.sampleFun()
                            }.await()
                        }
                    } finally {
                        isRunning = false
                    }
                }
            }
        ) {
            Text(
                text = stringResource(R.string.first_but)
            )
        }

        Spacer(modifier =  Modifier.height(Consts.DEFAULT_PADDING_DP))

        Button(
            enabled = !isRunning && count > 0,
            onClick = {
                job = customScope.launch {
                    isRunning = true

                    try {
                        val tasks = List(count) {
                            async {
                                repository.sampleFun()
                            }
                        }

                        tasks.awaitAll()
                    } finally {
                        isRunning = false
                    }
                }
            }
        ) {
            Text(
                text = stringResource(R.string.sec_but)
            )
        }

        Spacer(modifier =  Modifier.height(Consts.DEFAULT_PADDING_DP))

        Button(
            enabled = isRunning,
            onClick = {
                job?.cancel()
            }
        ) {
            Text(
                text = stringResource(R.string.third_but)
            )
        }

        if (isRunning) {
            CircularProgressIndicator()
        }
    }
}