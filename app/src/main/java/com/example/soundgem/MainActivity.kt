package com.example.soundgem

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.soundgem.ui.components.AudioButton
import com.example.soundgem.ui.components.Layout
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
            var currentSelectedSound by rememberSaveable {
                mutableStateOf<AudioFile?>(null)
            }
            val bottomSheetState = rememberModalBottomSheetState()
            Scaffold(
                topBar = { Layout.Header() },
                bottomBar = { Layout.Footer(onClickNew = { openBottomSheet = !openBottomSheet }) }
            ) { innerPadding ->
                AudioButton.LazyGrid(
                    files = files,
                    padding = innerPadding,
                    onSoundLongPress = {
                        currentSelectedSound = it
                        openBottomSheet = true
                    }
                )
                if (openBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { openBottomSheet = false },
                        sheetState = bottomSheetState,
                    ) {
                        Box(modifier = Modifier.height(250.dp)) {
                            Text(text = currentSelectedSound?.name ?: "none"  )
                        }
                    }
                }
            }
        }
    }
}
