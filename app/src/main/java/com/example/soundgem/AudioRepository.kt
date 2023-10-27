package com.example.soundgem

import io.github.jan.supabase.storage.BucketItem

interface AudioRepository {
    suspend fun fetchFilesFromBucket(): List<BucketItem>
    suspend fun getAllFilesFromBucket(): List<BucketItem>
}