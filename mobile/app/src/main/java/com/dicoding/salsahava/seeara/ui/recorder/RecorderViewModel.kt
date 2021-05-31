package com.dicoding.salsahava.seeara.ui.recorder

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.salsahava.seeara.data.source.RecordingRepository

class RecorderViewModel(private val recordingRepository: RecordingRepository) : ViewModel() {

    fun startRecording() = recordingRepository.startRecording()

    fun stopRecording() = recordingRepository.stopRecording()

    fun playRecording() = recordingRepository.playRecording()

    fun getDownloadUrl(): LiveData<Uri> = recordingRepository.getDownloadUrl()
}