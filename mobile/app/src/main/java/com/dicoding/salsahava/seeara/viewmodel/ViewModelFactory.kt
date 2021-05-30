package com.dicoding.salsahava.seeara.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.salsahava.seeara.core.di.Injection
import com.dicoding.salsahava.seeara.data.RecorderRepository
import com.dicoding.salsahava.seeara.ui.recorder.RecorderViewModel
import com.dicoding.salsahava.seeara.ui.history.HistoryViewModel

class ViewModelFactory private constructor(private val recorderRepository: RecorderRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(RecorderViewModel::class.java) -> {
                return RecorderViewModel(recorderRepository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                return HistoryViewModel() as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
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
