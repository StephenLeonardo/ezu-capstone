package com.dicoding.salsahava.seeara.recorder

import androidx.lifecycle.ViewModel

class RecorderViewModel(private val recorderRepository: RecorderRepository) : ViewModel() {

    fun startRecording() = recorderRepository.startRecording()

    fun stopRecording() = recorderRepository.stopRecording()

    fun playRecording() = recorderRepository.playRecording()

}