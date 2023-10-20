package com.example.soundgem
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://supabase.url")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val api: SoundboardApi by lazy {
        retrofit.create(SoundboardApi::class.java)
    }

}
