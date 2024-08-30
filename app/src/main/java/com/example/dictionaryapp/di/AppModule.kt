package com.example.dictionaryapp.di

import GsonParser
import android.app.Application
import androidx.room.Room
import com.example.dictionaryapp.core.util.Converters
import com.example.dictionaryapp.data.local.db.WordInfoDatabase
import com.example.dictionaryapp.data.remote.DictionaryApi
import com.example.dictionaryapp.domain.repository.WordInfoRepo
import com.example.dictionaryapp.domain.repository.WordInfoRepoImpl
import com.example.dictionaryapp.domain.usecase.GetWordInfo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGetWordInfoUseCases(repository: WordInfoRepo): GetWordInfo {
        return GetWordInfo(repository)
    }
    @Provides
    @Singleton
    fun provideDatabase(app: Application):WordInfoDatabase{
        return Room.databaseBuilder(
            app, WordInfoDatabase::class.java, "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }
    @Provides
    @Singleton
    fun provideWordInfoRepository(database: WordInfoDatabase,api: DictionaryApi):WordInfoRepo{
        return WordInfoRepoImpl(api,database.dao)
    }
    @Provides
    @Singleton
    fun provideDictionaryApi():DictionaryApi{
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
}









