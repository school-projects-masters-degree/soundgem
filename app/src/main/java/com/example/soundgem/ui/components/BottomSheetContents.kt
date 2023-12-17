package com.example.soundgem.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundgem.R
import com.example.soundgem.supabase.Audio
import com.example.soundgem.supabase.AudioViewModel
import com.example.soundgem.ui.layouts.ShareLayout
import java.io.File

enum class BottomSheetMode(val stringValue: String) {
    NEW("new"),
    SHARE("share")
}


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

        @Composable
        fun UploadNew(viewModel: AudioViewModel, onUploaded: () -> Unit){
            var text1 by remember { mutableStateOf("") }
            var text2 by remember { mutableStateOf("") }
            var text3 by remember { mutableStateOf("") }
            var selectedFileByteArray by remember { mutableStateOf<ByteArray?>(null) }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Inputs.CustomTextInput(
                    value = text1,
                    onValueChange = {newValue -> text1 = newValue},
                    label = "Name"
                )
                Inputs.CustomTextInput(
                    value = text2,
                    onValueChange = {newValue -> text2 = newValue},
                    label = "Description"
                )
                Inputs.CustomTextInput(
                    value = text3,
                    onValueChange = {newValue ->
                        if( newValue.isNotEmpty()) {
                            if(
                                newValue.length <= 2 &&
                                Character.isSurrogate(newValue[0])
                            ) {text3 = newValue}
                        } else {
                            text3 = ""
                        }
                    },
                    label = "Emoji"
                )
            }
            Column (modifier = Modifier.fillMaxWidth()){
                Inputs.FileInput(
                    onFileSelected = { fileByteArray ->
                        selectedFileByteArray = fileByteArray
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    isSelected = selectedFileByteArray != null
                )
                Box(modifier = Modifier.height(16.dp)){}
                Box(
                    modifier = Modifier
                        .background(colorResource(R.color.primary_500))
                        .clickable {
                            if (text1 != "" && text3 != ""){
                                val audio = Audio(
                                    audioTitle = text1,
                                    description = text2,
                                    emoji = text3,
                                    type = "mp3"
                                )
                                viewModel.uploadFileByteArray(selectedFileByteArray!!, audio)
                                onUploaded()
                            }
                        }
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center,
                ){
                    Text(modifier = Modifier
                            .padding(8.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        text = "Upload \uD83D\uDE80")
                }
            }
        }
    }
}