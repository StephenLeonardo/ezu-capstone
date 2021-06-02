package com.dicoding.salsahava.seeara.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.salsahava.seeara.databinding.HistoryFragmentBinding

class HistoryFragment : Fragment() {

    private var _fragmentHistoryBinding: HistoryFragmentBinding? = null
    private val binding get() = _fragmentHistoryBinding

    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentHistoryBinding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }
}