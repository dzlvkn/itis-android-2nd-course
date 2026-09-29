package com.example.android2ndcourse

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val surnameName = stringResource(R.string.surname_and_name)
            val group = stringResource(R.string.group)
            FirstScreen(
                surnameName = surnameName,
                group = group,
                onButtonClick = {
                    val intent = Intent(this@MainActivity, SecondActivity::class.java)
                    intent.putExtra("result", "$surnameName/$group")
                    startActivity(intent)
                }

            )
        }
    }
}

@Composable
fun FirstScreen(surnameName: String, group: String, onButtonClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            top = 64.dp
        )
    ) {
        Text(
            text = surnameName
        )
        Text(
            text = group,
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 8.dp
            )
        )
        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .size(200.dp, 50.dp),
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(
                pressedElevation = 8.dp
            )
        ) {
            Text(stringResource(R.string.button_text))
        }
    }
}