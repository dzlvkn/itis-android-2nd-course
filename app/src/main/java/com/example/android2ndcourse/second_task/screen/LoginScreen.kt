package com.example.android2ndcourse.second_task.screen

import android.net.Uri
import android.util.Patterns
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.android2ndcourse.R
import com.example.android2ndcourse.second_task.Consts

@Composable
fun LoginScreen(
    navController: NavHostController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .imePadding()
        ) {

            Text(
                text = stringResource(R.string.authorization),
                fontSize = 32.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Consts.DEFAULT_PADDING_DP,
                        end = Consts.DEFAULT_PADDING_DP,
                        top = Consts.DEFAULT_PADDING_DP
                    ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier =  Modifier.height(Consts.DEFAULT_PADDING_DP))


            val emailFromLogin = rememberSaveable { mutableStateOf("") }
            val passwordFromLogin = rememberSaveable { mutableStateOf("") }

            val emailError = remember { mutableStateOf(false) }
            val passwordError = remember { mutableStateOf(false) }

            val isEmailEmpty = remember {mutableStateOf(false)}
            val isPasswordEmpty = remember {mutableStateOf(false)}

            TextField(
                value = emailFromLogin.value,

                onValueChange = { input ->
                    emailError.value = false
                    isEmailEmpty.value = false
                    emailFromLogin.value = input
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Consts.DEFAULT_PADDING_DP,
                        end = Consts.DEFAULT_PADDING_DP,
                        top = Consts.DEFAULT_PADDING_DP
                    ),
                label = {
                    Text(text = stringResource(R.string.label_for_email))
                },
                supportingText = {
                    val supText: String = if (isEmailEmpty.value) {
                        stringResource(R.string.is_empty)
                    } else if (emailError.value){
                        stringResource(R.string.email_error)
                    } else {
                        ""
                    }
                    Text(text = supText)
                },
                isError = isEmailEmpty.value || emailError.value,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                maxLines = 1
            )

            Spacer(modifier =  Modifier.height(Consts.DEFAULT_PADDING_DP))

            TextField(
                value = passwordFromLogin.value,
                onValueChange = { input ->
                    passwordError.value = false
                    isPasswordEmpty.value = false
                    passwordFromLogin.value = input
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Consts.DEFAULT_PADDING_DP,
                        end = Consts.DEFAULT_PADDING_DP,
                    ),
                label = {
                    Text(text = stringResource(R.string.label_for_password))
                },
                supportingText = {
                    val supText: String = if (isPasswordEmpty.value) {
                        stringResource(R.string.is_empty)
                    } else if (passwordError.value){
                        stringResource(R.string.password_error)
                    } else {
                        ""
                    }
                    Text(text = supText)
                },
                isError = isPasswordEmpty.value || passwordError.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                maxLines = 1
            )

            Spacer(modifier =  Modifier.height(Consts.DEFAULT_PADDING_DP))

            Button(
                onClick = {
                    val emailPattern = Patterns.EMAIL_ADDRESS
                    val emailChek = (emailPattern.matcher(emailFromLogin.value)).matches()
                    val passwordCheck = CheckPassword(passwordFromLogin.value)

                    if (emailFromLogin.value.isEmpty()) {
                        isEmailEmpty.value = true
                        return@Button
                    }

                    if (passwordFromLogin.value.isEmpty()) {
                        isPasswordEmpty.value = true
                        return@Button
                    }

                    if (!emailChek) {
                        emailError.value = true
                        return@Button
                    }

                    if (!passwordCheck) {
                        passwordError.value = true
                        return@Button
                    }

                    navController.navigate(
                        "Result/${Uri.encode(emailFromLogin.value)}/${Uri.encode(passwordFromLogin.value)}"
                    )

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Consts.DEFAULT_PADDING_DP,
                        end = Consts.DEFAULT_PADDING_DP
                    )
            ) {
                Text(
                    text = stringResource(R.string.button_text)
                )
            }
        }
    }
}

fun CheckPassword(password: String): Boolean {
    val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+$")
    return (password.length >= 8) && (password.matches(regex))
}