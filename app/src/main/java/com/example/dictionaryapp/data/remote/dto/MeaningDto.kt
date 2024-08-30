package com.example.dictionaryapp.data.remote.dto

import com.example.dictionaryapp.domain.model.Meaning

data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String,
){
    fun toMeaning(): Meaning{
        return Meaning(
            definitions = definitions.map{it.toDefination()},
            partOfSpeech = partOfSpeech,
        )
    }
}