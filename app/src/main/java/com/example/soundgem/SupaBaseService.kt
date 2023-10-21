package com.example.soundgem
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
class SupaBaseService {

    fun getData(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.launch {
            val client = getClient()
            val response = client.postgrest["audio"].select()
            val data = response.decodeList<Audio>()
            Log.e("supabase", data.toString())
        }
    }

     private fun getClient(): SupabaseClient {
        val client = createSupabaseClient(
            supabaseUrl ="https://hstotxtfkjolmjqbrkka.supabase.co",
            supabaseKey ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhzdG90eHRma2pvbG1qcWJya2thIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTczNzg0ODYsImV4cCI6MjAxMjk1NDQ4Nn0.3EgSSvY9HgNjHEDT_4jAVnd4rwZKKP2bEP5kweJsmTU",
        ) {
            install(Postgrest)
        }
        return client
    }
    @Serializable
    data class Audio(
        val id: Int = 1,
        val audioTitle: String? = "",
        val type: String? = "mp3",
        val uri: String? = "",
        val emoji: String? = "😤",
        val description: String? = "",
        val amountShared: Int? = 0,
    )

}