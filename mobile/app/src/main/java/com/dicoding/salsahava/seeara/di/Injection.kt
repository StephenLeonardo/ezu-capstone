package com.dicoding.salsahava.seeara.di

import android.content.Context
import com.dicoding.salsahava.seeara.recorder.RecorderRepository

object Injection {

    fun provideRepository(context: Context): RecorderRepository =
        RecorderRepository.getInstance(context)
}