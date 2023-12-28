package com.example.soundgem.supabase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChecksumDto(
    @SerialName("id")
    val id: Int = 1,
    @SerialName("created_at")
    val createdAt: String? = "",
    @SerialName("checksum")
    val checksum: String? = "",
)
