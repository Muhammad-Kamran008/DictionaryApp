package com.example.dictionaryapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.core.util.OnItemDeleteListener
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.core.util.toast
import com.example.dictionaryapp.databinding.FragmentSearchedHistoryBinding
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.ui.adapters.SearchedWordsAdapter
import com.example.dictionaryapp.ui.components.MyDialog
import com.example.dictionaryapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchedHistoryFragment : Fragment(), OnItemDeleteListener {

    private val binding by lazy {
        FragmentSearchedHistoryBinding.inflate(layoutInflater)
    }

    private lateinit var searchedWordsAdapter: SearchedWordsAdapter
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }


    private fun setupRecyclerView() {
        searchedWordsAdapter = SearchedWordsAdapter { taskItem ->
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
                        searchedWordsAdapter.items = words.toMutableList()
                    }

                    is Resource.Error -> {
                    }
                }
            }
        }
    }


    private fun showDeleteConfirmationDialog(wordInfo: WordInfo) {
        val confirmDeleteDialog = MyDialog(
            requireActivity(),
            onConfirmDelete = {
                mainViewModel.deleteWordInfo(wordInfo)
                searchedWordsAdapter.deleteItem(wordInfo)
                requireActivity().toast("Data Deleted Successfully")

            },
            onCancel = {


            }
        )
        confirmDeleteDialog.show()
    }

    override fun onDeleteConfirmed(wordInfo: WordInfo) {
        mainViewModel.deleteWordInfo(wordInfo)
        requireActivity().toast("Data Deleted Successfully")
    }
}