package com.dicoding.salsahava.seeara.api

import android.net.Uri
import com.dicoding.salsahava.seeara.data.source.remote.response.RecordingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("predict/predict")
    suspend fun getRecording(
        @Query("filename") fileName: String,
        @Query("file_url") downloadUrl: Uri
    ): RecordingResponse
}