package com.example.dictionaryapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.dictionaryapp.core.util.OnItemSelectedListener
import com.example.dictionaryapp.databinding.BottomSheetWordBinding
import com.example.dictionaryapp.databinding.SearchedItemBinding
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.ui.components.InfoBottomSheet
import com.google.android.material.bottomsheet.BottomSheetDialog


class SearchedWordsAdapter(
    private val onDeleteClick: (WordInfo) -> Unit

) : RecyclerView.Adapter<SearchedWordsAdapter.SearchedWordsViewHolder>() {

    var items: MutableList<WordInfo> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field=value
            notifyDataSetChanged()
        }


    class SearchedWordsViewHolder(val binding: SearchedItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedWordsViewHolder {
        return SearchedWordsViewHolder(
            SearchedItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
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
            onDeleteClick.invoke(taskItem)
        }

        holder.binding.share.setOnClickListener {
            handleSendAction(position=position,context = holder.binding.share.context,)
        }
        holder.itemView.setOnClickListener {
            showBottomSheet(holder, position)
        }
    }


    private fun handleSendAction(context: Context,position: Int) {
        val currentWordInfo = items[position]
        val word = currentWordInfo.word

        val meanings = currentWordInfo.meanings.joinToString(separator = "\n\n") { meaning ->
            val definitions = meaning.definitions.joinToString(separator = "\n") { definition ->
                "- ${definition.definition}" + if (definition.example != null) "\n  Example: ${definition.example}" else ""
            }
            "Part of Speech: ${meaning.partOfSpeech}\nDefinitions:\n$definitions"
        }
        val shareText = """
        Word: $word

        Meanings:
        $meanings
    """.trimIndent()


        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
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

    @SuppressLint("ClickableViewAccessibility")
    private fun showBottomSheet(holder: SearchedWordsViewHolder, position: Int) {
        val currentWordInfo = items[position]
        InfoBottomSheet(context = holder.itemView.context, currentWordInfo).show()
    }

}


