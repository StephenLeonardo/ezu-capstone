package com.dicoding.salsahava.seeara.recorder

import android.content.Context
import android.content.ContextWrapper
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException
import java.lang.IllegalStateException

class RecorderRepository private constructor(context: Context) {

    private var mediaRecorder: MediaRecorder? = null
    private val directory = ContextWrapper(context).getExternalFilesDir(Environment.DIRECTORY_MUSIC)

    init {
        try {
            initRecorder()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()

            Log.d("Repo", "Started!")
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopRecording() {
        mediaRecorder?.stop()
        mediaRecorder?.release()

        Log.d("Repo", "Stopped!")

        initRecorder()
    }

    fun playRecording() {
        try {
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(getRecordingPath())
            mediaPlayer.prepare()
            mediaPlayer.start()

            Log.d("Repo", "Played!")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initRecorder() {
        mediaRecorder = MediaRecorder()

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder?.setOutputFile(getRecordingPath())
    }

    private fun getRecordingPath(): String {
        val file = File(directory, "recording" + ".mp3")
        return file.path
    }

    companion object {
        @Volatile
        private var instance: RecorderRepository? = null

        fun getInstance(context: Context): RecorderRepository =
            instance ?: synchronized(this) {
                instance ?: RecorderRepository(context).apply {
                    instance = this
                }
            }
    }

}