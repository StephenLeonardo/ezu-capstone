package com.dicoding.salsahava.seeara.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.salsahava.seeara.data.source.local.entity.RecordingEntity
import com.dicoding.salsahava.seeara.data.RecordingRepository

class HistoryViewModel(private val recordingRepository: RecordingRepository) : ViewModel() {

    fun getAllRecord(): LiveData<List<RecordingEntity>> = recordingRepository.getAllRecord()

}