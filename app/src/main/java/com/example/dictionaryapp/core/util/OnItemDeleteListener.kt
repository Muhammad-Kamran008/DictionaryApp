package com.example.dictionaryapp.core.util

import com.example.dictionaryapp.domain.model.WordInfo

interface OnItemDeleteListener {
    fun onDeleteConfirmed(wordInfo: WordInfo)
}

