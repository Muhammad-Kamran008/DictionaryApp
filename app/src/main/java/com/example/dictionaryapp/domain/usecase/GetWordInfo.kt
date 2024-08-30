package com.example.dictionaryapp.domain.usecase

import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.domain.repository.WordInfoRepo


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfo (
    private val repo: WordInfoRepo
){
    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if(word.isBlank()) {
            return flow {  }
        }
        return repo.getWordInfo(word)
    }

}

