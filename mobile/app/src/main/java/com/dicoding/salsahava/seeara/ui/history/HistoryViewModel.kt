package com.dicoding.salsahava.seeara.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.salsahava.seeara.data.entity.RecordingEntity
import com.dicoding.salsahava.seeara.data.source.RecordingRepository

class HistoryViewModel(private val recordingRepository: RecordingRepository) : ViewModel() {

    fun getAllRecord(): LiveData<List<RecordingEntity>> = recordingRepository.getAllRecord()

    fun insertRecord(record: RecordingEntity) {
        recordingRepository.insertRecord(record)
    }

    fun updateRecord(record: RecordingEntity) {
        recordingRepository.updateRecord(record)
    }

    fun deleteRecord(record: RecordingEntity) {
        recordingRepository.deleteRecord(record)
    }
}