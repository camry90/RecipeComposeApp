package com.example.recipecomposeapp.data.database.converter

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(text: String): List<String> {
        return if (text.isNotBlank()) {
            text.split("|||")
        } else {
            emptyList()
        }
    }

    @TypeConverter
    fun TypeConverter.fromList(list: List<String>): String = list.joinToString("|||")
}