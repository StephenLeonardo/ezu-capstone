package com.dicoding.salsahava.seeara.api

import com.dicoding.salsahava.seeara.data.source.remote.response.RecordingResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("predict")
    fun getRecording(
        @Field("filename") fileName: String,
        @Field("file_url") downloadUrl: String
    ): Call<RecordingResponse>
}