package com.example.dictionaryapp.data.local.db

import com.example.dictionaryapp.data.local.dao.WordInfoDao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionaryapp.core.util.Converters
import com.example.dictionaryapp.data.local.entities.WordInfoEntity

@Database(
    entities = [WordInfoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WordInfoDatabase: RoomDatabase() {
    abstract val dao: WordInfoDao
}



