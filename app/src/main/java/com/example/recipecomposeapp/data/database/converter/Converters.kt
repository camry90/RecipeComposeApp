package com.example.recipecomposeapp.data.database.converter

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(text: String): List<String> = text.split("|||")

    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString("|||")
}