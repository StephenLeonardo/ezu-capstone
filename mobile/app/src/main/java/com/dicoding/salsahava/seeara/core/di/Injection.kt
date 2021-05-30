package com.dicoding.salsahava.seeara.core.di

import android.content.Context
import com.dicoding.salsahava.seeara.data.RecorderRepository

object Injection {

    fun provideRepository(context: Context): RecorderRepository =
        RecorderRepository.getInstance(context)
}