package com.example.dictionaryapp.data.util

import java.lang.reflect.Type

interface JsonParser {
    fun<T> fromJson(json: String, type: Type): T?
    fun<T> toJSon(obj: T, type: Type):String?
}