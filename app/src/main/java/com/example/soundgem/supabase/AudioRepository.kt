package com.example.soundgem.supabase

import android.location.Location
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

    suspend fun updateLocation(location: Location) {

        val res =mapOf(
            "lat" to location.latitude,
            "long" to location.longitude,
        )
        println(res)
        supabaseClient.postgrest["user_loc"].insert(
            mapOf(
                "lat" to location.latitude,
                "long" to location.longitude,
            )
        )

    }

    // Choose a random hash from the database
    suspend fun getRandomChecksum(): String {
        val supabaseResponse = supabaseClient.postgrest["checksums"].select()
        val data = supabaseResponse.decodeList<ChecksumDto>()
        val checksum = data.random().checksum.toString()
        return checksum
    }

    suspend fun uploadFindings(dec: String) {
        supabaseClient.postgrest["dec"].insert(
            mapOf(
                "pt" to dec,
            )
        )
    }
}