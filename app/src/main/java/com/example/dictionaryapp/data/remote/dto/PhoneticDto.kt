package com.example.dictionaryapp.data.remote.dto

import com.example.dictionaryapp.domain.model.Phonetics


data class PhoneticDto(
    val audio: String?,
    val sourceUrl: String?,
    val text: String?
){
    fun toPhontics(): Phonetics {
        return Phonetics(
            audio = audio,
            sourceUrl = sourceUrl,
            text = text
        )
    }
}