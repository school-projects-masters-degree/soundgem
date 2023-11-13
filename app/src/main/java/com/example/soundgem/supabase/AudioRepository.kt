package com.example.soundgem.supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

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
}