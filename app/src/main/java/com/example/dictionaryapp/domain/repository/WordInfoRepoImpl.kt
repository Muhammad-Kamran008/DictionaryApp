package com.example.dictionaryapp.domain.repository

import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.data.local.dao.WordInfoDao
import com.example.dictionaryapp.data.remote.DictionaryApi
import com.example.dictionaryapp.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepoImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepo {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = wordInfos))

        try {
            val remoteWordInfos = api.getWordInfo(word)
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Sorry word doesn't found",
                    data = wordInfos
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Check your internet connection.",
                    data = wordInfos
                )
            )
        }

        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))

    }


    override fun deleteAll() {
        dao.deleteAllWordInfos()

    }

    override suspend fun delete(wordInfo: WordInfo) {
        dao.deleteWordInfos(listOf(wordInfo.word))
    }

    //    override fun getAllWordsInfo(): Flow<Resource<List<List<WordInfo>>>> = flow {
//        emit(Resource.Loading())
//
//        try {
//            val allWordInfos = dao.getAllWordInfos()
//            emit(Resource.Success(allWordInfos.map { list -> list.map { it.toWordInfo() } }))
//        } catch (e: Exception) {
//            emit(Resource.Error(message = "Error retrieving all words", data = emptyList()))
//        }
//    }
    override fun getAllWordsInfo(): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        try {
            val allWordInfos = dao.getAllWordInfos().map { it.toWordInfo() }
            emit(Resource.Success(allWordInfos))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Error retrieving all words", data = emptyList()))
        }
    }

}

