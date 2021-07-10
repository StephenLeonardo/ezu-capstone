package com.dicoding.salsahava.seeara.data

import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.salsahava.seeara.data.source.local.LocalDataSource
import com.dicoding.salsahava.seeara.data.source.local.entity.RecordingEntity
import com.dicoding.salsahava.seeara.data.source.remote.ApiResponse
import com.dicoding.salsahava.seeara.data.source.remote.RemoteDataSource
import com.dicoding.salsahava.seeara.data.source.remote.response.RecordingResponse
import com.dicoding.salsahava.seeara.utils.AppExecutors
import com.dicoding.salsahava.seeara.utils.Formatter
import com.dicoding.salsahava.seeara.vo.Resource
import java.io.IOException
import kotlin.random.Random

class RecordingRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val path: String,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
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

    override fun getDownloadUrl(): LiveData<Uri> {
        val downloadUrlResult = MutableLiveData<Uri>()

        remoteDataSource.getDownloadUrl(path, object : RemoteDataSource.LoadDownloadUrlCallback {
            override fun onDownloadUrlReceived(downloadUrlResponse: Uri) {
                downloadUrlResult.postValue(downloadUrlResponse)
            }
        })

        return downloadUrlResult
    }

    override fun getRecording(
        context: Context,
        downloadUrl: String
    ): LiveData<Resource<RecordingEntity>> {

        return object : NetworkBoundResource<RecordingEntity, RecordingResponse>(appExecutors) {
            val id = Random.nextInt()

            override fun loadFromDB(): LiveData<RecordingEntity> {
                return localDataSource.getRecordById(id)
            }

            override fun shouldFetch(data: RecordingEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<RecordingResponse>> =
                remoteDataSource.getRecording(context, downloadUrl)

            override fun saveCallResult(data: RecordingResponse) {
                val recording = RecordingEntity(
                    id,
                    data.fileName,
                    data.fileUrl,
                    data.translation,
                    Formatter.getCurrentDate()
                )

                localDataSource.insertRecord(recording)
            }
        }.asLiveData()
    }

    override fun getAllRecord(): LiveData<List<RecordingEntity>> =
        localDataSource.getAllRecord()

    override fun setRecorded(recordingEntity: RecordingEntity) {
        appExecutors.diskIO().execute { localDataSource.setRecorded(recordingEntity) }
    }

    companion object {
        @Volatile
        private var instance: RecordingRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            path: String,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): RecordingRepository =
            instance ?: synchronized(this) {
                instance ?: RecordingRepository(
                    remoteDataSource,
                    path,
                    localDataSource,
                    appExecutors
                ).apply {
                    instance = this
                }
            }
    }
}