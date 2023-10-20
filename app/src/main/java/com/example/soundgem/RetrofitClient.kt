package com.example.soundgem
import io.github.jan.supabase.BuildConfig
import io.github.jan.supabase.annotations.SupabaseInternal
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

    @OptIn(SupabaseInternal::class)
    val url = BuildConfig

}
