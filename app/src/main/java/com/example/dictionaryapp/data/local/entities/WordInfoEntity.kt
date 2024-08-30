package com.example.dictionaryapp.data.local.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dictionaryapp.core.util.PhoneticsTypeConverter

import com.example.dictionaryapp.domain.model.Meaning
import com.example.dictionaryapp.domain.model.Phonetics
import com.example.dictionaryapp.domain.model.WordInfo




@TypeConverters(PhoneticsTypeConverter::class)
@Entity
data class WordInfoEntity(
    val word: String,
    val phonetic: String?,
    val meanings: List<Meaning>,
    val phonetics: List<Phonetics>,
    @PrimaryKey val id: Int? = null
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            id = id,
            meanings = meanings,
            word = word,
            phonetic = phonetic,
            phonetics = phonetics
        )
    }
}


