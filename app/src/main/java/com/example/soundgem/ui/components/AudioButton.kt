package com.example.soundgem.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundgem.AudioFile
import com.example.soundgem.R

class AudioButton {
    companion object{
        @OptIn(ExperimentalFoundationApi::class)
        @Composable
        fun AudioButton(
            onClick: () -> Unit,
            modifier: Modifier = Modifier,
            onLongClick: (() -> Unit)? = null,
            onDoubleClick: (() -> Unit)? = null,
            backgroundColor: Color,
        ) {
            Surface(
                modifier = modifier.combinedClickable(
                    onClick = onClick,
                    onDoubleClick = onDoubleClick,
                    onLongClick = onLongClick,
                ),
                shape = MaterialTheme.shapes.extraLarge,
                color = backgroundColor,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Text(
                            text = "\uD83D\uDE00",
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                )
            }
        }


        @Composable
        fun LazyGrid(
            files: MutableList<AudioFile>,
            padding: PaddingValues,
        ) {
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
                                AudioButton(
                                    modifier = Modifier
                                        .height(80.dp)
                                        .width(80.dp),
                                    onClick = { println("click") },
                                    onLongClick = { println("long") },
                                    backgroundColor = colorResource(R.color.primary_300),
                                )
                                Text(
                                    text = file.name,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}