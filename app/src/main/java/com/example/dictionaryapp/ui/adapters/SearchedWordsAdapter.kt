package com.example.dictionaryapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.SearchedItemBinding
import com.example.dictionaryapp.domain.model.WordInfo

class SearchedWordsAdapter(
    private val items: MutableList<WordInfo>,
    private val context:Context,
    private val onDeleteClick: (WordInfo) -> Unit

) :

    RecyclerView.Adapter<SearchedWordsAdapter.SearchedWordsViewHolder>() {

    class SearchedWordsViewHolder(val binding: SearchedItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedWordsViewHolder {
        return SearchedWordsViewHolder(
            SearchedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchedWordsViewHolder, position: Int) {
        val taskItem = items[position]
        holder.binding.name.text = items[position].word
        holder.binding.delete.setOnClickListener {
            onDeleteClick(taskItem)
        }

        holder.binding.share.setOnClickListener {
            handleSendAction(position)
        }

    }


    private fun handleSendAction(position: Int) {
        val currentWordInfo = items[position]
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,currentWordInfo )
            type = "text/*"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share"))
    }


    private fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }


    fun deleteItem(wordInfo: WordInfo) {
        val position = items.indexOf(wordInfo)
        if (position != -1) {
            removeItem(position)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<WordInfo>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }


}

