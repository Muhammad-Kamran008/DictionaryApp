package com.example.dictionaryapp.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dictionaryapp.databinding.WordSearchBinding


class PartsOfSpeechAdapter(private val partsOfSpeech: List<String>) :
    RecyclerView.Adapter<PartsOfSpeechAdapter.PartsOfSpeechViewHolder>() {
    private var selectedPosition = RecyclerView.NO_POSITION
    private var itemClickListener: ((Int) -> Unit)? = null

    class PartsOfSpeechViewHolder(val binding: WordSearchBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartsOfSpeechViewHolder {
        return PartsOfSpeechViewHolder(
            WordSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return partsOfSpeech.size
    }

    override fun onBindViewHolder(holder: PartsOfSpeechViewHolder, position: Int) {
        val speeches = partsOfSpeech[position]
        holder.binding.word.text = speeches

        holder.binding.word.setBackgroundColor(
            if (position == selectedPosition) Color.CYAN else Color.TRANSPARENT
        )

        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(position)
        }

    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    fun updateSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }
}
