package com.example.soundgem.sharedPrefs

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class SharedPrefView(context: Context): ViewModel() {
    private val encryptedSharedPreferences = EncryptedSharedPreferences().getEncryptedSharedPreferences(context)

    fun saveData(key: String, value: String) {
        with (encryptedSharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun retrieveData(key: String): String? {
        return encryptedSharedPreferences.getString(key, null)
    }
}

@Composable
fun SharedPrefView(viewModel: SharedPrefView) {
    val data by remember {
        mutableStateOf(viewModel.retrieveData("data"))
        //mutableStateOf(viewModel.retrieveData("SUPABASE_ANON_KEY"))
    }
    Column {
        Text(text = data ?: "No Data")
        Button(onClick = {
            //viewModel.saveData("SUPABASE_ANON_KEY", BuildConfig.SUPABASE_ANON_KEY)
            viewModel.saveData("data", "Hello SharedPrefs")
        }) {
            Text(text = "Save Data")
        }
    }
}