package com.example.dictionaryapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Phonetics(
    val audio: String?,
    val sourceUrl: String?,
    val text: String?
):Parcelable

