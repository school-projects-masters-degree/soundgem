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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.soundgem.supabase.AudioDto
import com.example.soundgem.ui.components.AudioButton
import com.example.soundgem.ui.components.Layout
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import com.example.soundgem.supabase.Audio


class MainActivity : ComponentActivity() {
    private val filesLiveData = MutableLiveData<List<Audio>>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
        setContent {

            var openBottomSheet by rememberSaveable { mutableStateOf(false) }
            var currentSelectedSound by rememberSaveable {
                mutableStateOf<Audio?>(null)
            }
            val bottomSheetState = rememberModalBottomSheetState()
            val files = filesLiveData.observeAsState(initial = emptyList()).value
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
                            Text(text = currentSelectedSound?.audioTitle?: "none"  )
                        }
                    }
                }
            }
        }
    }
    private fun createClient(): SupabaseClient{
        val client = createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_ANON_KEY,
        ) {
            install(Postgrest)
        }
        return client
    }

    private fun getData() {
        lifecycleScope.launch {
            val client = createClient()
            val supabaseResponse =
                client.postgrest["audio"]
                    .select()
            val data = supabaseResponse.decodeList<AudioDto>()
            Log.d("MainActivity", "getData: $data")

            val audioFiles = data.map { dto ->
                Audio(
                    audioTitle = dto.audioTitle,
                    id = dto.id,
                    emoji = dto.emoji,
                    uri = dto.uri,
                    amountShared = dto.amountShared,
                )
            }

            // Update Livedata
            filesLiveData.postValue(audioFiles)
        }
    }
}
