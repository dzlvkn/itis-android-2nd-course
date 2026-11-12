package com.example.android2ndcourse.second_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android2ndcourse.second_task.screen.LoginScreen
import com.example.android2ndcourse.second_task.screen.NavRoute
import com.example.android2ndcourse.second_task.screen.SecondScreen
import kotlin.random.Random


class MainActivity2: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val password = rememberSaveable { mutableStateOf(GeneratePassword())}
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = NavRoute.Login.toString()
            ) {

                composable(route = NavRoute.Login.toString()) {
                    LoginScreen (
                        navController = navController
                    )
                }

                composable(
                    route = "Result/{emailFromLogin}/{passwordFromLogin}"
                ) {
                    backStackEntry ->
                    val email = backStackEntry.arguments?.getString("emailFromLogin")
                    val enteredPassword = backStackEntry.arguments?.getString("passwordFromLogin")
                    SecondScreen (
                        navController = navController,
                        generatedPassword = password.value,
                        enteredPassword = enteredPassword,
                        email = email
                    )
                }
            }

        }
    }
}

fun GeneratePassword(): String {
    val lowercaseLetters = "abcdefghijklmnopqrstuvwxyz"
    val uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val numbers = "0123456789"
    val allSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

    val lowLetter = lowercaseLetters[Random.nextInt(lowercaseLetters.length)]
    val uppLetter = uppercaseLetters[Random.nextInt(uppercaseLetters.length)]
    val num = numbers[Random.nextInt(numbers.length)]
    val passwordSize = Random.nextInt(8, 17)

    val charList: MutableList<Char> = mutableListOf()
    charList.add(lowLetter)
    charList.add(uppLetter)
    charList.add(num)

    while(charList.size < passwordSize) {
        charList.add(allSymbols[Random.nextInt(allSymbols.length)])
    }

    charList.shuffle()
    val password = charList.joinToString("")

    return password

}
