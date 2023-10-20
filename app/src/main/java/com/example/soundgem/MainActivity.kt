package com.example.soundgem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AudioFile(var name: String, var content: String)
val baseColor = Color(0x9875ff)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var file1 = AudioFile("file1", "content1")
        var file2 = AudioFile("file2", "content1")
        var file3 = AudioFile("file3", "content1")
        var file4 = AudioFile("file4", "content1")
        val files = mutableListOf<AudioFile>(file1, file2, file3, file4)
        setContent {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.white))
            ) {
                Header()
                AudioGrid(files)
            }
        }
    }


@Composable
fun AudioGrid(files: MutableList<AudioFile>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed( count = 2 ),
    ) {
        files.map { file -> item {
            Box(
                modifier = Modifier.height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                FilledTonalButton(
                    modifier = Modifier
                        .height(80.dp)
                        .width(150.dp),
                    onClick = { println(file.content) }) {
                    Text(file.name)
                }
            }
        } }
    }
}
}

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(colorResource(R.color.purple_200)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Soundgem",
            fontSize = 20.sp
        )
    }

}
