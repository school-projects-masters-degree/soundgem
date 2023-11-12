package com.example.soundgem.supabase
// Domain Object
data class Audio(
    val id: Int = 1,
    val audioTitle: String? = "",
    val type: String? = "mp3",
    val uri: String? = "",
    val emoji: String? = "😤",
    val description: String? = "",
    val amountShared: Int? = 0,
)