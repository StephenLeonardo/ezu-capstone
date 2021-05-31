package com.dicoding.salsahava.seeara.data.source.remote

import android.net.Uri
import android.util.Log
import com.dicoding.salsahava.seeara.utils.Formatter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.*

class RemoteDataSource {

    private val storage = Firebase.storage

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