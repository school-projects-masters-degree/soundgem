package com.example.soundgem.supabase

import kotlinx.serialization.Serializable

// Domain Object
@Serializable
data class Audio(
    val id: Int? = 1,
    val audioTitle: String? = "",
    var type: String? = "mp3",
    var uri: String? = "",
    val emoji: String? = "😤",
    val description: String? = "",
    var amountShared: Int? = 0,
)