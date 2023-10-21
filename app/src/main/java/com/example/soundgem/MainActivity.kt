package com.example.soundgem

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.soundgem.ui.theme.SoundGemTheme


data class AudioFile(var name: String, var content: String)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val supaBaseService = SupaBaseService()
        supaBaseService.getData(lifecycleScope)
        val file1 = AudioFile("Na hör mal", "content1")
        val file2 = AudioFile("over 9000", "content1")
        val file3 = AudioFile("NOOOO", "content1")
        val file4 = AudioFile("rickRoll", "content1")
        val files = mutableListOf<AudioFile>(file1, file2, file3, file4)
        (1..20).map { files.add(AudioFile("test", "content")) }
        setContent {
            Scaffold(
                topBar = { Header() },
                bottomBar = { Footer() }
            ) { innerPadding ->
                AudioGrid(files = files, padding = innerPadding)
            }
        }
    }


@Composable
fun AudioGrid(files: MutableList<AudioFile>, padding: PaddingValues) {
    LazyVerticalGrid(
        columns = GridCells.Fixed( count = 2 ),
        modifier = Modifier
            .padding(padding)
            .fillMaxHeight()
    ) {
        files.map { file -> item {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FilledTonalButton(
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = colorResource(R.color.primary_300),
                            contentColor = Color.Black
                        ),
                        elevation= ButtonDefaults.buttonElevation(12.dp),
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp),
                        onClick = { println(file.content) }){
                        Text(
                            text="\uD83D\uDE00",
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Text(
                        text = file.name,
                    )
    }
}
}


@Composable
fun Header() {
    rememberSystemUiController().setSystemBarsColor(
        color = colorResource(R.color.primary_500)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
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
            .height(70.dp)
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
