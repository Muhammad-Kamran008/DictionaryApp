package com.example.dictionaryapp.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DefinitionDto(
    val antonyms: List<String>,
    val definition: String,
    val example: String?,
    val synonyms: List<String>
):Parcelable{
    fun toDefination():DefinitionDto{
        return DefinitionDto(
            antonyms = antonyms,
            definition = definition,
            example = example,
            synonyms = synonyms
        )
    }
}