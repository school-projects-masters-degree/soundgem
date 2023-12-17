package com.example.soundgem.supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage

class AudioRepository(private val supabaseClient: SupabaseClient) {

    suspend fun getData(): List<Audio> {
        val supabaseResponse = supabaseClient.postgrest["audio"].select()
        val data = supabaseResponse.decodeList<AudioDto>()
        return data.map { dto ->
            Audio(
                audioTitle = dto.audioTitle,
                id = dto.id,
                emoji = dto.emoji,
                uri = dto.uri,
                amountShared = dto.amountShared,
            )
        }
    }

    //TODO: make this work
    /*suspend fun increaseAmountShared(id: Int) {
        val supabaseResponse = supabaseClient.postgrest["audio"]
            .update (mapOf("amountShared" to 17))
            .eq("id", 1L)
            .execute()
    }*/

    suspend fun downloadSoundFromUri(uri: String): ByteArray {
        val bucket = supabaseClient.storage["sounds"]
        val response = bucket.downloadPublic(uri)
        return response
    }

    suspend fun uploadSound(file: ByteArray, name: String): String {
        println("upload invoked")
        val bucket = supabaseClient.storage["sounds"]
        val fullUri = bucket.upload("${name}.mp3", file)
        return fullUri.substring(fullUri.lastIndexOf("/")+1)
    }

    suspend fun uploadSoundMetaInfo(audio: Audio){
        println(audio)
        supabaseClient.postgrest.from("audio").insert(audio)
    }


}