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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


data class AudioFile(var name: String, var content: String)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val supaBaseService = SupaBaseService()
        val files = supaBaseService.getData(lifecycleScope)
        lifecycleScope.launch {
            Log.d("ExecutionFlow", "Before fetching files")
            //val bucketItems = supaBaseService.getAllFilesFromBucket()
            Log.d("ExecutionFlow", "After fetching files")
            //Log.d("fileUrls", bucketItems.toString())
        }
        setContent {
            var openBottomSheet by rememberSaveable { mutableStateOf(false) }
            val bottomSheetState = rememberModalBottomSheetState()
            Scaffold(
                topBar = { Header() },
                bottomBar = { Footer(onClickNew = { openBottomSheet = !openBottomSheet }) }
            ) { innerPadding ->
                AudioGrid(files = files, padding = innerPadding)
                if (openBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { openBottomSheet = false },
                        sheetState = bottomSheetState,
                    ) {
                        Box(modifier = Modifier.height(250.dp))
                    }
                }
            }
        }
    }


    @Composable
    fun AudioGrid(files: MutableList<AudioFile>, padding: PaddingValues) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            modifier = Modifier
                .padding(padding)
                .fillMaxHeight()
        ) {
            files.map { file ->
                item {
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
                                elevation = ButtonDefaults.buttonElevation(12.dp),
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(80.dp),
                                onClick = { println(file.content) }) {
                                Text(
                                    text = "\uD83D\uDE00",
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
                text = "⟢ Soundgem ⟣",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.white)
            )
        }
    }

    @Composable
    fun Footer(
        onClickNew: (() -> Unit)? = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(colorResource(R.color.primary_500)),
            contentAlignment = Alignment.Center,
        ) {
            FilledTonalButton(
                onClick = { onClickNew?.invoke() },
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
}
