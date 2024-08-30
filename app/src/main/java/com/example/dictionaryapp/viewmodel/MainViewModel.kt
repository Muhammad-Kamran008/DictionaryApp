package com.example.dictionaryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.domain.repository.WordInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WordInfoRepo
) : ViewModel() {

//    private val _wordInfos = MutableStateFlow<Resource<List<List<WordInfo>>>>(Resource.Loading())
//    val wordInfos: StateFlow<Resource<List<List<WordInfo>>>> = _wordInfos.asStateFlow()


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

//    fun searchWord(word: String) {
//        viewModelScope.launch {
//            repository.getWordInfo(word).collect { result ->
//                _wordInfos.value = result
//            }
//        }
//    }

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


//private val getWordInfo: GetWordInfo,
//private val repo: WordInfoRepoImpl


//private val _distinctWords = MutableStateFlow<List<WordInfo>>(emptyList())
//    val distinctWords: StateFlow<List<WordInfo>> = _distinctWords
//
//    var _searchQuery = mutableStateOf("")
//    var searchQuery: State<String> = _searchQuery
//
//    var checkIfDarkmode = mutableStateOf(false)
//
//    fun setDarkmode(darkmode: Boolean) {
//        checkIfDarkmode.value = darkmode
//    }
//
//    private var _state = mutableStateOf(WordInfoState())
//    var state: State<WordInfoState> = _state
//
//    private val _eventFlow = MutableSharedFlow<UIEvent>()
//    val eventFlow = _eventFlow.asSharedFlow()
//
//    private var searchJob: Job? = null
//
//    fun onSearch(query: String) {
//        _searchQuery.value = query
//        searchJob?.cancel()
//        searchJob = viewModelScope.launch {
//            delay(500L)
//            getWordInfo(query)
//                .onEach { result ->
//                    when (result) {
//                        is Resource.Success -> {
//                            _state.value = state.value.copy(
//                                wordInfoItems = result.data ?: emptyList(),
//                                isLoading = false
//                            )
//                        }
//                        is Resource.Error -> {
//                            _state.value = state.value.copy(
//                                wordInfoItems = result.data ?: emptyList(),
//                                isLoading = false
//                            )
//                            _eventFlow.emit(
//                                UIEvent.ShowSnackbar(
//                                    result.message ?: "Unknown error"
//                                )
//                            )
//                        }
//                        is Resource.Loading -> {
//                            _state.value = state.value.copy(
//                                wordInfoItems = result.data ?: emptyList(),
//                                isLoading = true
//                            )
//                        }
//
//                        else -> {}
//                    }
//                }.launchIn(this)
//        }
//    }
//
//    fun deleteAll(){
//        repo.deleteAll()
//    }
//    sealed class UIEvent {
//        data class ShowSnackbar(val message: String): UIEvent()
//    }
