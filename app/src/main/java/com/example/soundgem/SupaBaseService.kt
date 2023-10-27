package com.example.soundgem
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.BucketItem
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.lang.Exception

class SupaBaseService {

    fun getData(lifecycleScope: LifecycleCoroutineScope): MutableList<AudioFile> {
        lifecycleScope.launch {
            val client = getClient()
            val response = client.postgrest["audio"].select()
            val data = response.decodeList<Audio>()
            Log.d("supabase db fetch", data.toString())
        }
        val file1 = AudioFile("Na hör mal", "content1")
        val file2 = AudioFile("over 9000", "content1")
        val file3 = AudioFile("NOOOO", "content1")
        val file4 = AudioFile("rickRoll", "content1")
        val files = mutableListOf<AudioFile>(file1, file2, file3, file4)
        (1..20).map { files.add(AudioFile("test", "content")) }
        return files
    }

     private fun getClient(): SupabaseClient {
         val supaBaseUrl = BuildConfig.SUPABASE_URL
         val supaBaseKey = BuildConfig.SUPABASE_ANON_KEY
        val client = createSupabaseClient(
            supabaseUrl = supaBaseUrl,
            supabaseKey = supaBaseKey
        ) {
            install(Postgrest)
            install(Storage)
        }
        return client
    }

    /*suspend fun fetchFilesFromBucket(): List<BucketItem> {
        val listOfFiles = mutableListOf<String>()


        return getAllFilesFromBucket()
    }*/

    /*suspend fun getAllFilesFromBucket(): List<BucketItem> {
        val bucketName = "sounds"
        val client = getClient()
        val bucket = client.storage[bucketName]

        return withContext(Dispatchers.IO) {
            try {
                val filesInBucket = bucket.list()
                filesInBucket.forEach() {
                    file ->
                    Log.d("file", file.toString())
                }
                filesInBucket
            } catch (e: Exception) {
                Log.e("Error", "An Error occured", e)
                listOf()
            }
        }
    }*/
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