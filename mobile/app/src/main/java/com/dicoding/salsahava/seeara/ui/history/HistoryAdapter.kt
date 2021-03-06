package com.dicoding.salsahava.seeara.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.salsahava.seeara.R
import com.dicoding.salsahava.seeara.data.source.local.entity.RecordingEntity
import com.dicoding.salsahava.seeara.databinding.ItemListHistoryBinding

class HistoryAdapter :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var listRecording = ArrayList<RecordingEntity>()

    fun setRecorded(recording: List<RecordingEntity>) {
        this.listRecording.clear()
        this.listRecording.addAll(recording)
        this.notifyDataSetChanged()
    }

    fun addItem(recording: RecordingEntity) {
        this.listRecording.add(recording)
        notifyItemInserted(this.listRecording.size - 1)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listRecording[position])
    }

    override fun getItemCount(): Int = listRecording.size

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListHistoryBinding.bind(itemView)

        fun bind(history: RecordingEntity) {
            binding.tvHistoryDate.text = history.date
            binding.tvHistoryTranslation.text = history.translation
        }
    }
}