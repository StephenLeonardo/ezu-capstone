package com.dicoding.salsahava.seeara.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.salsahava.seeara.R
import com.dicoding.salsahava.seeara.databinding.ItemListHistoryBinding
import com.dicoding.salsahava.seeara.entity.History

class HistoryAdapter(private val activity: Activity) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){

    var listHistory = ArrayList<History>()
        set(listHistory) {
            this.listHistory.clear()
            this.listHistory.addAll(listHistory)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

    override fun getItemCount(): Int = listHistory.size

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListHistoryBinding.bind(itemView)

        fun bind(history: History) {
            binding.tvHistoryDesc.text = history.description
        }
    }

}