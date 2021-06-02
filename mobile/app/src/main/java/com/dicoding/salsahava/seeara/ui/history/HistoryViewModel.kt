package com.dicoding.salsahava.seeara.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.salsahava.seeara.data.entity.RecordingEntity
import com.dicoding.salsahava.seeara.data.source.RecordingRepository

class HistoryViewModel(private val recordingRepository: RecordingRepository) : ViewModel() {

    fun getAllRecord(): LiveData<List<RecordingEntity>> = recordingRepository.getAllRecord()

    fun insertRecord(record: List<RecordingEntity>) {
        recordingRepository.insertRecord(record)
    }

    fun deleteRecord(record: List<RecordingEntity>) {
        recordingRepository.deleteRecord(record)
    }
}