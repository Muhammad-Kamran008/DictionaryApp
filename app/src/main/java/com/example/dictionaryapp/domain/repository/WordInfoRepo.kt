package com.example.dictionaryapp.domain.repository
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.data.local.entities.WordInfoEntity
import com.example.dictionaryapp.domain.model.WordInfo
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.Flow


interface WordInfoRepo {
    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
    fun deleteAll()
    //fun getAllWordsInfo():Flow<Resource<List<List<WordInfo>>>>
    fun getAllWordsInfo():Flow<Resource<List<WordInfo>>>
    suspend fun delete(wordInfo: WordInfo)
}


