package com.dicoding.salsahava.seeara.ui.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.salsahava.seeara.databinding.HistoryFragmentBinding
import com.dicoding.salsahava.seeara.ui.adapter.HistoryAdapter
import com.dicoding.salsahava.seeara.utils.Formatter
import com.dicoding.salsahava.seeara.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private var _fragmentHistoryBinding: HistoryFragmentBinding? = null
    private val binding get() = _fragmentHistoryBinding

    private var viewModel: HistoryViewModel? = null
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentHistoryBinding = HistoryFragmentBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(context as Context)
            viewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]

            binding?.rvHistory?.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }

            adapter = HistoryAdapter(requireActivity())

            loadHistoryAsync()
        }
    }

    private fun loadHistoryAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredHistory = async(Dispatchers.IO) {
                val recordings = viewModel?.getAllRecord()
                Formatter.mapRecordingFromDatabaseToArrayList(recordings)
            }

            val history = deferredHistory.await()
            if (history.size > 0) adapter.listRecording = history
            else {
                adapter.listRecording = ArrayList()
            }
        }
    }
}