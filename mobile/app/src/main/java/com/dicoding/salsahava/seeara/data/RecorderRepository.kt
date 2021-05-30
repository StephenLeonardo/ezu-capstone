package com.dicoding.salsahava.seeara.data

import android.content.Context
import android.content.ContextWrapper
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RecorderRepository(context: Context) {

    private var mediaRecorder: MediaRecorder? = null
    private val path =
        ContextWrapper(context).getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath + "/recording.mp3"
    private val storage = Firebase.storage

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

        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopRecording() {
        mediaRecorder?.stop()
        mediaRecorder?.reset()
        mediaRecorder?.release()

        initRecorder()
    }

    fun playRecording() {
        try {
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepare()
            mediaPlayer.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initRecorder() {
        mediaRecorder = MediaRecorder()

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder?.setOutputFile(path)
    }

    fun uploadAudio() {
        val storageRef = storage.reference
        val audioRef = storageRef.child("audios").child("${UUID.randomUUID()}.mp3")

        val uri = Uri.fromFile(File(path))

        val metadata = StorageMetadata.Builder()
            .setCustomMetadata("date", getCurrentDate())
            .build()

        audioRef.putFile(uri, metadata).addOnSuccessListener {
            Log.d("Repo", "File uploaded")
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd, hh:mm:ss", Locale.getDefault())
        return sdf.format(Date())
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