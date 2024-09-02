package com.example.dictionaryapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.WordLayoutBinding
import com.example.dictionaryapp.domain.model.Meaning

class BottomSheetInfoAdapter(
    private val meanings: List<Meaning>,
) :
    RecyclerView.Adapter<BottomSheetInfoAdapter.LayoutViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION
    private var itemClickListener: ((Int) -> Unit)? = null

    class LayoutViewHolder(val binding: WordLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayoutViewHolder {
        return LayoutViewHolder(
            WordLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return meanings.size
    }


    override fun onBindViewHolder(holder: LayoutViewHolder, position: Int) {

        val tvDefinition: TextView = holder.binding.definition
        val tvExample: TextView = holder.binding.example

        meanings[position].let {
            if (it.definitions.isNotEmpty()) {
                tvDefinition.text = "Definition: " + it.definitions[0].definition
                tvExample.text = "Examples: " + (it.definitions[0].example ?: "")
                tvExample.text =
                    if (!it.definitions[0].example.isNullOrEmpty()) "Examples: " + it.definitions[0].example else ""
            } else {
                tvDefinition.text = "No definition available"
                tvExample.text = "No example available"
            }
        }

//        holder.itemView.setOnClickListener {
//            listener.onItemSelected(position)
//        }


    }

    fun updateSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }

}


