package com.example.soundgem.supabase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundgem.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.launch

class AudioViewModel : ViewModel() {

    private val repository = AudioRepository(createClient())
    val filesLiveData = MutableLiveData<List<Audio>>()

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
        }
        return client
    }

}