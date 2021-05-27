package com.dicoding.salsahava.seeara.record

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dicoding.salsahava.seeara.databinding.FragmentRecordBinding
import java.io.File
import java.lang.Exception

class RecordFragment : Fragment() {

    private var _fragmentRecordBinding: FragmentRecordBinding? = null
    private val binding get() = _fragmentRecordBinding

    private var mediaRecorder: MediaRecorder? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentRecordBinding = FragmentRecordBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mediaRecorder = MediaRecorder()

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder?.setOutputFile(getRecordingPath())

        binding?.btnStart?.setOnClickListener {
            val permission = android.Manifest.permission.RECORD_AUDIO

            if (ContextCompat.checkSelfPermission(
                    context as Context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity as Activity,
                    arrayOf(android.Manifest.permission.RECORD_AUDIO), 0
                )
            } else startRecording()
        }

        binding?.btnStop?.setOnClickListener { stopRecording() }
        binding?.btnPlay?.setOnClickListener { playRecording() }
    }

    private fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()

            Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        mediaRecorder?.stop()
        mediaRecorder?.release()
        mediaRecorder = null

        Toast.makeText(context, "Recording stopped", Toast.LENGTH_SHORT).show()
    }

    private fun playRecording() {
        try {
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(getRecordingPath())
            mediaPlayer.prepare()
            mediaPlayer.start()

            Toast.makeText(context, "Playing recording", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getRecordingPath(): String {
        val output = ContextWrapper(context).getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(output, "testRecordingFile" + ".mp3")
        return file.path
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentRecordBinding = null
    }
}