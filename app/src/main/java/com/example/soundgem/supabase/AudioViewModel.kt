package com.example.soundgem.supabase

import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundgem.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class AudioViewModel : ViewModel() {

    private val repository = AudioRepository(createClient())
    val filesLiveData = MutableLiveData<List<Audio>>()
    var currentFile = MutableLiveData<File>()
    private var mediaPlayer: MediaPlayer? = null

    fun fetchData() {
        viewModelScope.launch {
            val audioFiles = repository.getData()
            filesLiveData.postValue(audioFiles)
        }
    }

    private fun createClient(): SupabaseClient{
        val client = createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_ANON_KEY,
        ) {
            install(Postgrest)
            install(Storage)
        }
        return client
    }

    fun downloadAndPlaySound(uri: String, name: String?) {
        viewModelScope.launch {
            try {
                val audioData = repository.downloadSoundFromUri(uri)
                val tempFile = createTempAudioFile(audioData, "SoundGem_$name")
                playAudioFile(tempFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun downloadSound(uri: String, name: String?) {
            viewModelScope.launch {
                try {
                    val audioData = repository.downloadSoundFromUri(uri)
                    val tempFile = createTempAudioFile(audioData, "SoundGem_$name")
                    currentFile.postValue(tempFile);
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }

    private fun createTempAudioFile(audioData: ByteArray, name: String?): File {
        val tempFile = File.createTempFile(name ?: "gem", ".mp3", File("/data/data/com.example.soundgem/files/"))
        FileOutputStream(tempFile).use { fos ->
            fos.write(audioData)
        }
        return tempFile
    }
    fun playAudioFile(file: File) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(file.absolutePath)
            prepare()
            start()
            // Release when finished
            setOnCompletionListener {
                it.release()
                file.deleteOnExit()
            }
        }
    }

    fun uploadFileByteArray(file: ByteArray, audio: Audio) {
        viewModelScope.launch {
            try {
                val url = repository.uploadSound(file, audio.audioTitle!!)
                val enrichedAudio = audio.copy();
                enrichedAudio.uri = url
                enrichedAudio.type = "mp3"
                enrichedAudio.amountShared = 0
                repository.uploadSoundMetaInfo(enrichedAudio)
                fetchData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}