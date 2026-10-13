package com.example.android2ndcourse.first_test

import android.os.Bundle
import androidx.compose.material3.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil3.compose.AsyncImage
import androidx.compose.material3.Text

class MainActivity2: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "first_screen") {
        composable("first_screen") {
            FirstScreen(navController = navController)
        }
        composable("second_screen") {
            SecondScreen(navController = navController)
        }
    }
}

object SongHolder {
    var selectedSong: Song? = null
}

@Composable
fun FirstScreen(navController: NavHostController) {

    val songs = remember {
        listOf(
            Song(
                imageLink = "https://avatars.yandex.net/get-music-content/4399834/0415f8b8.a.17439837-1/m1000x1000",
                songTitle = "Плевок в вечность",
                singer = "LSP"
            ),
            Song(
                imageLink = "https://avatars.yandex.net/get-music-content/5412783/a3cafcec.a.19444071-1/m1000x1000",
                songTitle = "GENDER",
                singer = "SQWOZ BAB"
            ),
            Song(
                imageLink = "https://avatars.yandex.net/get-music-content/9837520/803771a6.a.32589300-2/m1000x1000",
                songTitle = "Turbo",
                singer = "Big Baby Tape"
            ),
            Song(
                imageLink = "https://avatars.yandex.net/get-music-content/14350511/3d3749a7.a.32589300-2/m1000x1000",
                songTitle = "Ну-ка снимай",
                singer = "MAYOT"
            ),
            Song(
                imageLink = "https://avatars.yandex.net/get-music-content/4399834/0415f8b8.a.17439837-1/m1000x1000",
                songTitle = "Ползать",
                singer = "LSP"
            ),
            Song(
                imageLink = "https://avatars.mds.yandex.net/i?id=d1cb84728090abdc9f77d2efb1cc810ba379f7c3-4901917-images-thumbs&n=13",
                songTitle = "Лил мами",
                singer = "OG Buda"
            ),
            Song(
                imageLink = "https://avatars.yandex.net/get-music-content/16406615/cdd1df81.a.39362975-1/m1000x1000",
                songTitle = "Терминатор",
                singer = "LSP"
            ),
            Song(
                imageLink = "https://avatars.yandex.net/get-music-content/5412783/a3cafcec.a.19444071-1/m1000x1000",
                songTitle = "DISNEY",
                singer = "SQWOZ BAB"
            ),
            Song(
                imageLink = "https://cdn-image.zvuk.com/pic?hash=b5e1ea72-642c-40bc-a118-a86dcd8ff3ea&id=36536331&size=large&type=release",
                songTitle = "Chuchuka",
                singer = "Big Baby Tape"
            ),
            Song(
                imageLink = "https://cdn-image.zvuk.com/pic?hash=c852a1b9-03cb-4c67-80ca-38c7ed4cc994&id=22386773&size=large&type=release",
                songTitle = "Москва",
                singer = "Yanix"
            )
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        items(songs) { song ->
            Text(
                text = "${song.songTitle}",
                modifier = Modifier
                    .clickable{
                        SongHolder.selectedSong = song
                        navController.navigate("second_screen")
                    }
            )

        }
    }
}

@Composable
fun SecondScreen(navController: NavController) {

    val song = SongHolder.selectedSong
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = song?.imageLink,
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        song?.songTitle?.let { Text(text = it, fontSize = 22.sp) }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Исполнитель: ${song?.singer}", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад", fontSize = 18.sp)
        }
    }

}

