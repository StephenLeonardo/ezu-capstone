package com.dicoding.salsahava.seeara.ui.recorder

import androidx.lifecycle.ViewModel
import com.dicoding.salsahava.seeara.data.RecorderRepository

class RecorderViewModel(private val recorderRepository: RecorderRepository) : ViewModel() {

    fun startRecording() = recorderRepository.startRecording()

    fun stopRecording() = recorderRepository.stopRecording()

    fun playRecording() = recorderRepository.playRecording()

    fun uploadAudio() = recorderRepository.uploadAudio()

}