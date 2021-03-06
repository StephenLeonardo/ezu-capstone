package com.dicoding.salsahava.seeara.di

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.Environment
import com.dicoding.salsahava.seeara.data.source.local.room.RecordingDatabase
import com.dicoding.salsahava.seeara.data.source.local.LocalDataSource
import com.dicoding.salsahava.seeara.data.RecordingRepository
import com.dicoding.salsahava.seeara.data.source.remote.RemoteDataSource
import com.dicoding.salsahava.seeara.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): RecordingRepository {

        val db = RecordingDatabase.getDatabase(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val path =
            ContextWrapper(context).getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath + "/recording.mp3"

        val localDataSource = LocalDataSource.getInstance(db.recordingDao())
        val appExecutors = AppExecutors()

        return RecordingRepository.getInstance(remoteDataSource, path, localDataSource, appExecutors)
    }
}