package com.example.soundgem.ui.layouts

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.soundgem.R
import java.io.File

class ShareLayout {
    companion object {
        @Composable
        fun ShareButton(
            sound: File?
        ){
            val context = LocalContext.current
            val shareLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Toast.makeText(context, "Shared data successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Sharing failed", Toast.LENGTH_SHORT).show()
                }
            }

            Box(
                modifier = Modifier
                    .background(colorResource(R.color.primary_500))
                    .clickable {
                        if(sound !== null){
                            val uri = FileProvider.getUriForFile(context, "com.example.soundgem.fileprovider", sound)
                            println(uri)
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                type = "audio/mp3"
                            }
                            sendIntent.putExtra(Intent.EXTRA_STREAM, uri)
                            val chooserIntent = Intent.createChooser(sendIntent, "Share this Gem")
                            shareLauncher.launch(chooserIntent)
                            sound.deleteOnExit()
                        } else {
                            Toast.makeText(context, "Something went wrong, sound file not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center,
            ){
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    text = "Share this GEM!",

                )
            }
        }
    }

}