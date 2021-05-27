package com.dicoding.salsahava.seeara.recorder

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.salsahava.seeara.databinding.FragmentRecorderBinding
import com.dicoding.salsahava.seeara.viewmodel.ViewModelFactory

class RecorderFragment : Fragment() {

    private var _fragmentRecordBinding: FragmentRecorderBinding? = null
    private val binding get() = _fragmentRecordBinding

    private var viewModel: RecorderViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentRecordBinding = FragmentRecorderBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(context as Context)
            viewModel = ViewModelProvider(this, factory)[RecorderViewModel::class.java]

            binding?.fabStart?.isEnabled = true
            binding?.fabPlay?.isEnabled = false

            binding?.fabStart?.setOnClickListener {
//                val permissions = arrayOf(
//                    android.Manifest.permission.RECORD_AUDIO,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                )
//
//                if (checkSelfPermission(
//                        context as Context,
//                        permissions[0]
//                    ) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
//                        context as Context,
//                        permissions[1]
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    requestPermissions(
//                        permissions, 0
//                    )
//                } else startRecording()
                startRecording()
            }

            binding?.fabStop?.setOnClickListener { stopRecording() }
            binding?.fabPlay?.setOnClickListener { playRecording() }
        }
    }

    private fun startRecording() {
        viewModel?.startRecording()

        binding?.fabStart?.visibility = View.INVISIBLE
        binding?.fabStop?.visibility = View.VISIBLE
        binding?.fabPlay?.isEnabled = false
        Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show()
    }

    private fun stopRecording() {
        viewModel?.stopRecording()

        binding?.fabStart?.visibility = View.VISIBLE
        binding?.fabStop?.visibility = View.INVISIBLE
        binding?.fabPlay?.isEnabled = true
        Toast.makeText(context, "Recording stopped", Toast.LENGTH_SHORT).show()
    }

    private fun playRecording() {
        viewModel?.playRecording()

        binding?.fabStart?.isEnabled = true
        binding?.fabPlay?.isEnabled = true

        Toast.makeText(context, "Playing recording", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentRecordBinding = null
    }
}