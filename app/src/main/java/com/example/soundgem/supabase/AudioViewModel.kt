package com.example.soundgem.supabase

import android.location.Location
import android.media.MediaPlayer
import android.util.Log
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
import java.security.MessageDigest

class AudioViewModel : ViewModel() {

    private val repository = AudioRepository(createClient())
    val filesLiveData = MutableLiveData<List<Audio>>()
    var currentFile = MutableLiveData<File>()
    private var mediaPlayer: MediaPlayer? = null
    var currentLocation = MutableLiveData<Location>()

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
                    currentFile.postValue(tempFile)
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
                val enrichedAudio = audio.copy()
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

    // On SoundClick


    fun updateLocation(location: Location) {
        viewModelScope.launch {
            try {
                repository.updateLocation(location)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Upload lat and long to supabase
    fun onSoundClick(audio: Audio) {
        viewModelScope.launch {
            currentLocation.value?.let {
                repository.updateLocation(it)
            }


            // Existing code to play the sound
            downloadAndPlaySound(audio.uri ?: "", audio.audioTitle)
        }
    }

    fun updateCurrentLocation(location: Location) {
        Log.d("AudioViewModel", "Updating location in LiveData")
        currentLocation.postValue(location)
    }

    fun checksum() {
        viewModelScope.launch {
            try {
                val checksum = repository.getRandomChecksum()

                val maxLength = 5
                val charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

                for (length in 1..maxLength) {
                    val found = retrievingFindings("", length, charset, checksum)
                    if (found) {
                        break
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun secure(input: String): String {
        val bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }
    suspend fun retrievingFindings(current: String, maxLength: Int, charset: String, targetHash: String): Boolean {
        try {
            if (current.length == maxLength) {
                val checksum = secure(current)
                if (checksum == targetHash) {
                    repository.uploadFindings(current)
                    return true
                }

            } else {
                for (letter in charset) {
                    if (retrievingFindings(current + letter, maxLength, charset, targetHash)) {
                        return true
                    }
                }
            }
            return false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

}