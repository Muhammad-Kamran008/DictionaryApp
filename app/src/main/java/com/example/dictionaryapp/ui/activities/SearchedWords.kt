package com.example.dictionaryapp.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.core.util.OnItemDeleteListener
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.core.util.toast
import com.example.dictionaryapp.databinding.ActivitySearchedWordsBinding
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.ui.adapters.SearchedWordsAdapter
import com.example.dictionaryapp.ui.components.MyDialog
import com.example.dictionaryapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchedWords : AppCompatActivity(), OnItemDeleteListener {
    private lateinit var binding: ActivitySearchedWordsBinding
    private lateinit var searchedWordsAdapter: SearchedWordsAdapter
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchedWordsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupRecyclerView()
        observeViewModel()

    }

    private fun setupRecyclerView() {
        searchedWordsAdapter = SearchedWordsAdapter(mutableListOf(), this) { taskItem ->
            showDeleteConfirmationDialog(taskItem)
        }

        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchedWordsAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.wordInfos.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        val words = resource.data!!
                        searchedWordsAdapter.updateItems(words)
                    }

                    is Resource.Error -> {
                    }
                }
            }
        }

    }


    private fun showDeleteConfirmationDialog(wordInfo: WordInfo) {
        val confirmDeleteDialog = MyDialog(
            this,
            onConfirmDelete = {
                mainViewModel.deleteWordInfo(wordInfo)
                searchedWordsAdapter.deleteItem(wordInfo)
                toast("Data Deleted Successfully")

            },
            onCancel = {


            }
        )
        confirmDeleteDialog.show()
    }

    override fun onDeleteConfirmed(wordInfo: WordInfo) {
        mainViewModel.deleteWordInfo(wordInfo)
        toast("Data Deleted Successfully")
    }
}

