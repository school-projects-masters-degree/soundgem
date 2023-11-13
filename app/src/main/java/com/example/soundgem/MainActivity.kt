package com.example.soundgem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.soundgem.supabase.AudioViewModel
import com.example.soundgem.ui.layouts.AppUI


class MainActivity : ComponentActivity() {
    private val viewModel: AudioViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchData()
        setContent {
            AppUI(viewModel = viewModel)
        }
    }
}
