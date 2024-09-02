package com.example.dictionaryapp.ui.components

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.dictionaryapp.databinding.BottomSheetWordBinding
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.ui.adapters.BottomSheetInfoAdapter
import com.example.dictionaryapp.ui.adapters.PartsOfSpeechAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class InfoBottomSheet(
    context: Context,
    private val currentWordInfo: WordInfo,

    ) : BottomSheetDialog(context) {
    private val binding by lazy {
        BottomSheetWordBinding.inflate(LayoutInflater.from(context))
    }
    private lateinit var partsOfSpeechAdapter: PartsOfSpeechAdapter
    private lateinit var bottomSheetInfoAdapter: BottomSheetInfoAdapter
    private lateinit var snapHelper: SnapHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val partsOfSpeech = currentWordInfo.meanings.map { it.partOfSpeech }
        snapHelper = PagerSnapHelper()

        partsOfSpeechAdapter = PartsOfSpeechAdapter(partsOfSpeech)
        binding.rvPartsOfSpeech.adapter = partsOfSpeechAdapter
        binding.rvPartsOfSpeech.layoutManager = GridLayoutManager(context, partsOfSpeech.size)
        snapHelper.attachToRecyclerView(binding.rvPartsOfSpeech)


        bottomSheetInfoAdapter = BottomSheetInfoAdapter(currentWordInfo.meanings)
        binding.rvlayout.adapter = bottomSheetInfoAdapter
        binding.rvlayout.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        snapHelper.attachToRecyclerView(binding.rvlayout)
        synchronizeRecyclerViews(
            firstRecyclerView = binding.rvPartsOfSpeech,
            secondRecyclerView = binding.rvlayout
        )
    }

    private fun synchronizeRecyclerViews(
        firstRecyclerView: RecyclerView, secondRecyclerView: RecyclerView
    ) {

        secondRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val pagePosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                if (pagePosition != -1) {
                    Log.d("Position", "onScrolled: $pagePosition")
                    firstRecyclerView.scrollToPosition(pagePosition)
                    partsOfSpeechAdapter.updateSelectedPosition(pagePosition)
                }
            }
        })

        partsOfSpeechAdapter.setOnItemClickListener { position ->
            secondRecyclerView.scrollToPosition(position)
            partsOfSpeechAdapter.updateSelectedPosition(position)
            bottomSheetInfoAdapter.updateSelectedPosition(position)
        }
    }
}

