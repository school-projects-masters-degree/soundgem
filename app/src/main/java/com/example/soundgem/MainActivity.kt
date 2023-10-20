package com.example.soundgem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AudioFile(var name: String, var content: String)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var file1 = AudioFile("Na hör mal", "content1")
        var file2 = AudioFile("over 9000", "content1")
        var file3 = AudioFile("NOOOO", "content1")
        var file4 = AudioFile("rickRoll", "content1")
        val files = mutableListOf<AudioFile>(file1, file2, file3, file4)
        setContent {
            Column (
                verticalArrangement= Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.white))
            ) {
                Header()
                AudioGrid(files)
                Footer()
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
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = colorResource(R.color.primary_300),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .height(80.dp)
                        .width(150.dp),
                    onClick = { println(file.content) }) {
                    Text(
                        text = file.name,
                        color = colorResource(R.color.primary_900),
                        fontSize = 18.sp,
                    )
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
            .background(colorResource(R.color.primary_500)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Soundgem",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.white)
        )
    }
}

@Composable
fun Footer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(colorResource(R.color.primary_500)),
        contentAlignment = Alignment.Center,
    ) {
        FilledTonalButton(
            onClick = {/*TODO*/},
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = colorResource(R.color.primary_200),
                contentColor = Color.Black
            ),
            modifier = Modifier
                .height(45.dp)
        ) {
            Text(
                text = "New",
                fontSize = 20.sp,
                color = colorResource(R.color.primary_700)
            )
        }
    }
}
