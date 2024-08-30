package com.example.dictionaryapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Defination(
    val antonyms: List<String>,
    val definition: String,
    val example: String?,
    val synonyms: List<String>
):Parcelable


