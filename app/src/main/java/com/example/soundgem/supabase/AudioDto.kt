package com.example.soundgem.supabase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
// Data transfer object
@Serializable
data class AudioDto(
    @SerialName("id")
    val id: Int = 1,

    @SerialName("audioTitle")
    val audioTitle: String? = "",

    @SerialName("type")
    val type: String? = "mp3",

    @SerialName("uri")
    val uri: String? = "",

    @SerialName("emoji")
    val emoji: String? = "😤",

    @SerialName("description")
    val description: String? = "",

    @SerialName("amountShared")
    val amountShared: Int? = 0,
)