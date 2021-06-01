package com.dicoding.salsahava.seeara.data.source

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.salsahava.seeara.data.entity.RecordingEntity
import com.dicoding.salsahava.seeara.data.source.remote.RemoteDataSource
import com.dicoding.salsahava.seeara.data.source.remote.response.RecordingResponse
import com.dicoding.salsahava.seeara.utils.Formatter
import java.io.IOException

class RecordingRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val path: String
) : RecordingDataSource {

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

    override fun getDownloadUrl(): LiveData<Uri> {
        val downloadUrlResult = MutableLiveData<Uri>()

        remoteDataSource.getDownloadUrl(path, object : RemoteDataSource.LoadDownloadUrlCallback {
            override fun onDownloadUrlReceived(downloadUrlResponse: Uri) {
                downloadUrlResult.postValue(downloadUrlResponse)
            }
        })

        return downloadUrlResult
    }

    override fun getRecording(context: Context, downloadUrl: String): LiveData<RecordingEntity> {
        val recordingResult = MutableLiveData<RecordingEntity>()

        remoteDataSource.getRecording(context, downloadUrl,
            object : RemoteDataSource.LoadRecordingCallback {
                override fun onRecordingReceived(recordingResponse: RecordingResponse) {
                    val recording = RecordingEntity(
                        recordingResponse.fileName,
                        recordingResponse.fileUrl,
                        recordingResponse.translation,
                        Formatter.getCurrentDate()
                    )

                    recordingResult.postValue(recording)
                }
            })

        return recordingResult
    }

    companion object {
        @Volatile
        private var instance: RecordingRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource, path: String): RecordingRepository =
            instance ?: synchronized(this) {
                instance ?: RecordingRepository(remoteDataSource, path).apply {
                    instance = this
                }
            }
    }
}