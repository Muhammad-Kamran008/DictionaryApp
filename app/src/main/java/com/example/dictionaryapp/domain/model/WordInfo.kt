package com.example.dictionaryapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class WordInfo(
    val id: Int? = null,
    val meanings: List<Meaning>,
    val phonetic: String?,
    val word: String,
    val phonetics: List<Phonetics>
):Parcelable


