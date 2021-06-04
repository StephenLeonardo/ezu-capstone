package com.dicoding.salsahava.seeara.ui.recorder

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.salsahava.seeara.data.entity.RecordingEntity
import com.dicoding.salsahava.seeara.databinding.FragmentRecorderBinding
import com.dicoding.salsahava.seeara.ui.adapter.HistoryAdapter
import com.dicoding.salsahava.seeara.viewmodel.ViewModelFactory

class RecorderFragment : Fragment() {

    private var _fragmentRecordBinding: FragmentRecorderBinding? = null
    private val binding get() = _fragmentRecordBinding

    private var viewModel: RecorderViewModel? = null
    private lateinit var adapter: HistoryAdapter

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

            adapter = HistoryAdapter(requireActivity())

            binding?.fabStart?.setOnClickListener {
                val permissions = arrayOf(
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

                if (checkSelfPermission(
                        requireContext(),
                        permissions[0]
                    ) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                        requireContext(),
                        permissions[1]
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        requireActivity(), permissions, 0
                    )
                } else startRecording()
            }
            binding?.fabStop?.setOnClickListener { stopRecording() }
        }
    }

    private fun startRecording() {
        viewModel?.startRecording()

        binding?.fabStart?.visibility = View.INVISIBLE
        binding?.fabStop?.visibility = View.VISIBLE
        Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show()
    }

    private fun stopRecording() {
        showLoading(true)
        binding?.cvTranslation?.tvTranslation?.visibility = View.INVISIBLE
        viewModel?.stopRecording()
        viewModel?.getDownloadUrl()?.observe(viewLifecycleOwner, { downloadUrl ->
            viewModel?.getRecording(requireContext(), downloadUrl.toString())
                ?.observe(viewLifecycleOwner, { recording ->
                    if (recording.translation == "") {
                        viewModel?.insertRecord(recording as RecordingEntity)
                        showLoading(false)
                    }
                    else {
                        showLoading(false)
                        binding?.cvTranslation?.tvTranslation?.visibility = View.VISIBLE
                        binding?.cvTranslation?.tvTranslation?.text = recording.translation
                        adapter.addItem(recording)
                    }
                })
        })

        binding?.fabStart?.visibility = View.VISIBLE
        binding?.fabStop?.visibility = View.INVISIBLE
        Toast.makeText(context, "Recording stopped", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(status: Boolean) {
        if (status) binding?.cvTranslation?.progressBar?.visibility = View.VISIBLE
        else binding?.cvTranslation?.progressBar?.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentRecordBinding = null
    }
}