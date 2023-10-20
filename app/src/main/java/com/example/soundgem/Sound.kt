package com.example.soundgem

import retrofit2.http.GET


interface SoundboardApi {
    @GET("Endpoint")

    suspend fun getSounds(): List<Sound>
}
data class Sound (
    val id: Int,
    val name: String,
    val description: String,
    val url: String

)