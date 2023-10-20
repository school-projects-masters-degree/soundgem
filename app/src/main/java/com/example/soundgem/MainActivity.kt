package com.example.soundgem

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.soundgem.ui.theme.SoundGemTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val apiKey = SecurePreferences.encryptedPreferences.getString("SUPABASE_KEY", "")
        setContent {
            SoundGemTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Retrieve the API key

                    // Display the API key using a Text composable
                    if (apiKey != null) {
                        Text(text = "API Key: $apiKey")
                    } else {
                        Text(text = "bla")
                    }
                }
            }
        }

    }

    @SuppressLint("StaticFieldLeak")
    object SecurePreferences: Application() {
        val context: Context = applicationContext
        val encryptedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            context,
            "secret_prefs",
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        init {
            val editor: SharedPreferences.Editor = encryptedPreferences.edit()
            editor.putString(
                "SUPABASE_KEY",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhzdG90eHRma2pvbG1qcWJya2thIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTczNzg0ODYsImV4cCI6MjAxMjk1NDQ4Nn0.3EgSSvY9HgNjHEDT_4jAVnd4rwZKKP2bEP5kweJsmTU"
            )
            editor.apply()
        }

        // Function to retrieve the API key
        /*fun getApiKey(): String {
            return encryptedPreferences.getString("SUPABASE_KEY", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhzdG90eHRma2pvbG1qcWJya2thIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTczNzg0ODYsImV4cCI6MjAxMjk1NDQ4Nn0.3EgSSvY9HgNjHEDT_4jAVnd4rwZKKP2bEP5kweJsmTU") ?: "not found"
        }*/
    }


}
