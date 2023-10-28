package com.example.soundgem.sharedPrefs

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedPrefView(context: Context): ViewModel() {
    private val encryptedPrefs = EncryptedSharedPreferences().getEncryptedSharedPreferences(context)

    val data: MutableLiveData<String> = MutableLiveData()


    fun loadData(key: String) {
        data.value = encryptedPrefs.getString(key, null)
    }

    fun saveData(key: String, value: String) {
        with(encryptedPrefs.edit()) {
            putString(key, value)
            apply()
        }
        loadData(key)
    }
}

@Composable
fun SharedPrefView(viewModel: SharedPrefView) {

    val data by viewModel.data.observeAsState(initial = "")
    var textFieldValue by remember { mutableStateOf(data) }

    Column {
        TextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                textFieldValue = newValue
            },
            label = { Text("Enter data") }
        )

        Button(onClick = {
            viewModel.saveData("data", textFieldValue)
        }) {
            Text(text = "Save Data")
        }

        Text(text = "Saved data: ${data ?: "No Data"}")
    }
}