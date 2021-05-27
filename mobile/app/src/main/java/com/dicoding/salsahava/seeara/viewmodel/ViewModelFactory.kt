package com.dicoding.salsahava.seeara.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.salsahava.seeara.di.Injection
import com.dicoding.salsahava.seeara.recorder.RecorderRepository
import com.dicoding.salsahava.seeara.recorder.RecorderViewModel

class ViewModelFactory private constructor(private val recorderRepository: RecorderRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(RecorderViewModel::class.java) -> {
                return RecorderViewModel(recorderRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }
}