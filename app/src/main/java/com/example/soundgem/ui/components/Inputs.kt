package com.example.soundgem.ui.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundgem.R
import java.io.IOException

class Inputs {
    companion object {
        @Composable
        fun CustomTextInput(
            value: String,
            onValueChange: (String) -> Unit,
            label: String
        ) {
            val customTextFieldColors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(R.color.primary_100),
                unfocusedContainerColor = colorResource(R.color.primary_50),
                disabledContainerColor = colorResource(R.color.secondary_50),
                focusedIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = colorResource(R.color.primary_500),
                focusedLabelColor = colorResource(R.color.primary_500),
                cursorColor = colorResource(R.color.primary_500),
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = colorResource(R.color.primary_200),
                unfocusedLabelColor = colorResource(R.color.primary_300)
            )
            val backgroundShape = RoundedCornerShape(12.dp)


            TextField(
                colors = customTextFieldColors,
                shape = backgroundShape,
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        @Composable
        fun FileInput(
            onFileSelected: (ByteArray) -> Unit,
            modifier: Modifier = Modifier,
            isSelected: Boolean?
        ) {
            val context = LocalContext.current
            val pickFileLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { resultUri ->
                try {
                    val inputStream = context.contentResolver.openInputStream(resultUri!!)
                    val byteArray = inputStream?.readBytes()
                    inputStream?.close()
                    onFileSelected(byteArray!!)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            Box(modifier = modifier
                .background(colorResource(R.color.primary_500))
                .clickable {
                    pickFileLauncher.launch("audio/*")
                }
                .fillMaxWidth()
                .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    text = if(isSelected == true) {"\uD83D\uDD25  File Selected  \uD83D\uDD25"} else {"Choose File"},
                )
            }
        }
    }
}