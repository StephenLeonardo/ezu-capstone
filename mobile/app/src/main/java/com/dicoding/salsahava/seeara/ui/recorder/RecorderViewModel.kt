package com.dicoding.salsahava.seeara.ui.recorder

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.salsahava.seeara.data.source.RecorderRepository

class RecorderViewModel(private val recorderRepository: RecorderRepository) : ViewModel() {

    fun startRecording() = recorderRepository.startRecording()

    fun stopRecording() = recorderRepository.stopRecording()

    fun playRecording() = recorderRepository.playRecording()

    fun getDownloadUrl(): LiveData<Uri> = recorderRepository.getDownloadUrl()
}