package com.example.dictionaryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.domain.repository.WordInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WordInfoRepo
) : ViewModel() {

    private val _wordInfos = MutableStateFlow<Resource<List<WordInfo>>>(Resource.Loading())
    val wordInfos: StateFlow<Resource<List<WordInfo>>> = _wordInfos.asStateFlow()


    init {
        getAllWordsInfo()
    }

    private fun getAllWordsInfo() {
        viewModelScope.launch {
            repository.getAllWordsInfo().collect { result ->
                _wordInfos.value = result
            }
        }
    }


   fun searchWord(word: String) {
        viewModelScope.launch {
            repository.getWordInfo(word).collect { result ->
                _wordInfos.value = result
            }
        }
    }

    fun deleteWordInfo(wordInfo: WordInfo) {
        viewModelScope.launch {
            repository.delete(wordInfo = wordInfo)
        }

    }

    fun deleteAllWordsInfo() {
        viewModelScope.launch {
            repository.deleteAll()
            getAllWordsInfo()
        }
    }
}




