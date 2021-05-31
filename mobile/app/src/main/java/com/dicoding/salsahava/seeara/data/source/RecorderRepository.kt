package com.dicoding.salsahava.seeara.data.source

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.salsahava.seeara.data.source.remote.RemoteDataSource
import java.io.IOException

class RecorderRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val path: String
) {

    private var mediaRecorder: MediaRecorder? = null

    init {
        try {
            initRecorder()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initRecorder() {
        mediaRecorder = MediaRecorder()

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setOutputFile(path)
    }

    fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()

        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopRecording() {
        mediaRecorder?.stop()
        mediaRecorder?.reset()
        mediaRecorder?.release()

        initRecorder()
    }

    fun playRecording() {
        try {
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepare()
            mediaPlayer.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getDownloadUrl(): LiveData<Uri> {
        val translationDownloadUrl = MutableLiveData<Uri>()

        remoteDataSource.getDownloadUrl(path, object : RemoteDataSource.LoadDownloadUrlCallback {
            override fun onDownloadUrlReceived(downloadUrl: Uri) {
                translationDownloadUrl.postValue(downloadUrl)
            }
        })

        return translationDownloadUrl
    }

    companion object {
        @Volatile
        private var instance: RecorderRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource, path: String): RecorderRepository =
            instance ?: synchronized(this) {
                instance ?: RecorderRepository(remoteDataSource, path).apply {
                    instance = this
                }
            }
    }
}