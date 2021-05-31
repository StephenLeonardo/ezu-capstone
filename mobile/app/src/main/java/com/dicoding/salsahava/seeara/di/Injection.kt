package com.dicoding.salsahava.seeara.di

import android.content.Context
import android.content.ContextWrapper
import android.os.Environment
import com.dicoding.salsahava.seeara.data.source.RecorderRepository
import com.dicoding.salsahava.seeara.data.source.remote.RemoteDataSource

object Injection {

    fun provideRepository(context: Context): RecorderRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        val path = ContextWrapper(context).getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath + "/recording.mp3"

        return RecorderRepository.getInstance(remoteDataSource, path)
    }
}