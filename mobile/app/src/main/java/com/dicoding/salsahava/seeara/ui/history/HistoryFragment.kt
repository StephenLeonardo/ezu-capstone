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
import com.dicoding.salsahava.seeara.viewmodel.ViewModelFactory

class HistoryFragment : Fragment() {

    private var _fragmentHistoryBinding: HistoryFragmentBinding? = null
    private val binding get() = _fragmentHistoryBinding

    private var viewModel: HistoryViewModel? = null

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

            val adapter = HistoryAdapter()
            binding?.progressBar?.visibility = View.VISIBLE
            viewModel?.getAllRecord()?.observe(viewLifecycleOwner, { recorded ->
                binding?.progressBar?.visibility = View.GONE
                adapter.setRecorded(recorded)
                adapter.notifyDataSetChanged()
            })

            binding?.rvHistory?.layoutManager = LinearLayoutManager(context)
            binding?.rvHistory?.setHasFixedSize(true)
            binding?.rvHistory?.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentHistoryBinding = null
    }
}