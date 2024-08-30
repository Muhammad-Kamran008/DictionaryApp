package com.example.dictionaryapp.core.util
import androidx.room.TypeConverter
import com.example.dictionaryapp.domain.model.Phonetics
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class PhoneticsTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<Phonetics> {
        val listType = object : TypeToken<List<Phonetics>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Phonetics>): String {
        return Gson().toJson(list)
    }
}