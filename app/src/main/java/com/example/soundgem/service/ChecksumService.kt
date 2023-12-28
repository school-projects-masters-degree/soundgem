package com.example.soundgem.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.soundgem.supabase.AudioRepository
import com.example.soundgem.supabase.AudioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChecksumService: Service() {

    private val viewModel: AudioViewModel by lazy {
        AudioViewModel()
    }
    override fun onBind(intent: Intent?): IBinder? {
       return null;
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.checksum()
        }
        return START_STICKY
    }

}