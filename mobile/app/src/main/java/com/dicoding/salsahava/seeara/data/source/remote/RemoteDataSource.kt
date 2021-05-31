package com.dicoding.salsahava.seeara.data.source.remote

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dicoding.salsahava.seeara.api.ApiConfig
import com.dicoding.salsahava.seeara.data.source.remote.response.RecordingResponse
import com.dicoding.salsahava.seeara.utils.Formatter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.io.File
import java.util.*

@FlowPreview
@ExperimentalCoroutinesApi
class RemoteDataSource {

    private val storage = Firebase.storage
    private val broadcastChannel = BroadcastChannel<String>(Channel.CONFLATED)

    fun getDownloadUrl(path: String, callback: LoadDownloadUrlCallback) {
        val storageRef = storage.reference
        val audioRef = storageRef.child("audios").child("${UUID.randomUUID()}.mp3")

        val uri = Uri.fromFile(File(path))

        val metadata = StorageMetadata.Builder()
            .setCustomMetadata("date", Formatter.getCurrentDate())
            .build()

        audioRef.putFile(uri, metadata).addOnSuccessListener {
            Log.d("Repo", "File uploaded")
            audioRef.downloadUrl.addOnSuccessListener {
                callback.onDownloadUrlReceived(it)
            }
        }
    }

    fun getRecording(fileName: String, downloadUrl: Uri): LiveData<RecordingResponse> {
        return broadcastChannel.asFlow()
            .debounce(300)
            .distinctUntilChanged()
            .filter {
                it.trim().isNotEmpty()
            }
            .mapLatest {
                ApiConfig.provideApiService().getRecording(fileName, downloadUrl)
            }
            .asLiveData()
    }

    interface LoadDownloadUrlCallback {
        fun onDownloadUrlReceived(downloadUrl: Uri)
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }
}