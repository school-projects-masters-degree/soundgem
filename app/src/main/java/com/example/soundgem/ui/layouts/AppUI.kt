package com.example.soundgem.ui.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.soundgem.supabase.Audio
import com.example.soundgem.supabase.AudioViewModel
import com.example.soundgem.ui.components.AudioButton
import com.example.soundgem.ui.components.Layout


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUI(viewModel: AudioViewModel) {
    val files by viewModel.filesLiveData.observeAsState(initial = emptyList())
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var currentSelectedSound by rememberSaveable {
        mutableStateOf<Audio?>(null)
    }
    val bottomSheetState = rememberModalBottomSheetState()
    Scaffold(
        topBar = { Layout.Header() },
        bottomBar = { Layout.Footer(onClickNew = { openBottomSheet = !openBottomSheet }) }
    ) { innerPadding ->

        AudioButton.LazyGrid(
            files = files,
            padding = innerPadding,

            ) {
            currentSelectedSound = it
            openBottomSheet = true
        }
        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
            ) {
                Box(modifier = Modifier.height(250.dp)) {
                    Text(text = currentSelectedSound?.audioTitle ?: "none")
                }
            }
        }
    }
}