package com.example.soundgem.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundgem.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class Layout {
    companion object {
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
}