package com.example.dictionaryapp.domain.model
import android.os.Parcelable
import com.example.dictionaryapp.data.remote.dto.DefinitionDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meaning(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String
):Parcelable

