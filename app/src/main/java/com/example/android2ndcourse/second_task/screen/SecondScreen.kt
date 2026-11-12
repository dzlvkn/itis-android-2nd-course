package com.example.android2ndcourse.second_task.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.android2ndcourse.R
import com.example.android2ndcourse.second_task.Consts

@Composable
fun SecondScreen(
    navController: NavHostController,
    generatedPassword: String,
    enteredPassword: String?,
    email: String?
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {

        val passwordIsCorrect = "https://webcalypt.cloud/storage/memes/109713/20252/vA7yXh9LwvkLBTD9OJpTFVJ4J5h9wCfYduugsniF5b2gDxYu6TbPGaA3Sztc3qTRT7WNY8F5ECKnA90l1cn1oazg1yIJRRcS3NfNfbDA8aRwxmySKKjOCliSmqxAYsnK.jpeg"
        val passwordIsIncorrect = "https://avatars.mds.yandex.net/i?id=17d50a59accca027dd4acfe953f4bef4_l-5118143-images-thumbs&n=13"

        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val image = if (enteredPassword == generatedPassword) {
                passwordIsCorrect
            } else {
                passwordIsIncorrect
            }

            AsyncImage(
                model = image,
                contentDescription = "",
                modifier = Modifier
                    .size(300.dp),
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Consts.DEFAULT_PADDING_DP,
                        end = Consts.DEFAULT_PADDING_DP
                    )

            ) {

                val isPasswordVisible = remember { mutableStateOf(false) }

                TextField(
                    value = generatedPassword,
                    onValueChange = {
                    },
                    modifier = Modifier
                        .padding(),
                    readOnly = true,
                    visualTransformation = if (isPasswordVisible.value) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    }

                )

                Spacer(modifier = Modifier.width(Consts.DEFAULT_PADDING_DP))

                Button(
                    onClick = {
                        isPasswordVisible.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = stringResource(R.string.button_for_watching_the_password)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Consts.DEFAULT_PADDING_DP))

            Button(
                onClick = {
                    navController.navigate(NavRoute.Login.toString())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Consts.DEFAULT_PADDING_DP,
                        end = Consts.DEFAULT_PADDING_DP
                    )
            ) {
                Text(
                    text = stringResource(R.string.button_try_again))
            }
        }
    }
}