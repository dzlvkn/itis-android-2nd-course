package com.example.android2ndcourse.third_task.screen

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.android2ndcourse.R
import com.example.android2ndcourse.second_task.Consts
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.location.LocationManager
import android.os.Build

@Composable
fun LocationScreen(
    ctx: Context
) {

    val context = ctx

    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val determine = stringResource(R.string.determine_the_coordinates)
    val needPermission = stringResource(R.string.need_permission_for_determine)
    var instruction by rememberSaveable() { mutableStateOf(determine) }

    fun getCoordinates() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.getCurrentLocation(
                    LocationManager.GPS_PROVIDER,
                    null,
                    context.mainExecutor
                ) {
                        location ->
                    if (location != null) {
                        val lat = location.latitude
                        val long = location.longitude
                        instruction = "Широта: $lat\nДолгота: $long"
                    } else {
                        instruction = "Не удалось определить координаты"
                    }
                }
            } else {
                return
            }
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            instruction = needPermission
        } else {
            getCoordinates()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = Consts.DEFAULT_PADDING_DP,
                    end = Consts.DEFAULT_PADDING_DP
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = instruction,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            Button(
                onClick = {
                    val hasPermission = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                        getCoordinates()
                    } else {
                        locationPermissionLauncher.launch(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    }
                },
            ) {
                Text(
                    text = stringResource(R.string.button_for_determine)
                )

            }

        }
    }

}
