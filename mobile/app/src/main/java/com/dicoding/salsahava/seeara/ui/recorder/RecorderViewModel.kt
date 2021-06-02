package com.dicoding.salsahava.seeara.ui.recorder

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.salsahava.seeara.data.source.RecordingRepository

class RecorderViewModel(private val recordingRepository: RecordingRepository) : ViewModel() {

    fun startRecording() = recordingRepository.startRecording()

    fun stopRecording() = recordingRepository.stopRecording()

    fun getDownloadUrl(): LiveData<Uri> = recordingRepository.getDownloadUrl()

    fun getRecording(context: Context, downloadUrl: String) =
        recordingRepository.getRecording(context, downloadUrl)
}