package com.example.soundgem.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundgem.R
import com.example.soundgem.supabase.Audio
import com.example.soundgem.supabase.AudioViewModel
import com.example.soundgem.ui.layouts.ShareLayout
import java.io.File

class BottomSheetContents {
    companion object{
        @Composable
        fun ShareContent(selectedSound: Audio?, soundFile: File?, viewModel: AudioViewModel) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    color = colorResource(R.color.primary_500),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    text = selectedSound?.audioTitle ?: "-")
                AudioButton.AudioButton(
                    modifier = Modifier
                        .height(128.dp)
                        .width(128.dp)
                        .padding(24.dp),
                    onClick = {
                        viewModel.downloadAndPlaySound(
                            selectedSound?.uri ?: "",
                            selectedSound?.audioTitle
                        )
                    },
                    backgroundColor = colorResource(R.color.primary_500),
                    emojiText = selectedSound?.emoji ?: ""
                )
            }
            ShareLayout.ShareButton(sound = soundFile)
        }
    }
}