package com.dicoding.salsahava.seeara.data.source.remote

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.salsahava.seeara.api.ApiConfig
import com.dicoding.salsahava.seeara.data.source.remote.response.RecordingResponse
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class RemoteDataSource {

    private val storage = Firebase.storage
    private val fileName = "${UUID.randomUUID()}.mp3"

    fun getDownloadUrl(path: String, callback: LoadDownloadUrlCallback) {
        val storageRef = storage.reference
        val audioRef = storageRef.child("audios").child(fileName)

        val uri = Uri.fromFile(File(path))

        audioRef.putFile(uri).addOnSuccessListener {
            Log.d(TAG, "File uploaded")
            audioRef.downloadUrl.addOnSuccessListener {
                callback.onDownloadUrlReceived(it)
            }
        }
    }

    fun getRecording(context: Context, downloadUrl: String): LiveData<ApiResponse<RecordingResponse>> {
        val recordingResult = MutableLiveData<ApiResponse<RecordingResponse>>()

        val client = ApiConfig.provideApiService().getRecording(fileName, downloadUrl)
        client.enqueue(object : Callback<RecordingResponse> {
            override fun onResponse(
                call: Call<RecordingResponse>,
                response: Response<RecordingResponse>
            ) {
                if (response.isSuccessful) {
                    val recording = response.body() as RecordingResponse

                    if (recording.message != "Success") {
                        Toast.makeText(
                            context,
                            "Maaf, kami tidak dapat mendengar suaranya. Apakah dapat diulang?",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d(TAG, recording.message)
                    }

                    recordingResult.value = ApiResponse.success(recording)
                }
            }

            override fun onFailure(call: Call<RecordingResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        return recordingResult
    }

    interface LoadDownloadUrlCallback {
        fun onDownloadUrlReceived(downloadUrlResponse: Uri)
    }

    interface LoadRecordingCallback {
        fun onRecordingReceived(recordingResponse: RecordingResponse)
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }

        private const val TAG = "RemoteDataSource"
    }
}