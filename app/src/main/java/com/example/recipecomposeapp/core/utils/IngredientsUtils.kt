package com.example.recipecomposeapp.core.utils

import com.example.recipecomposeapp.data.model.IngredientDto
import kotlinx.serialization.json.Json

val json = Json

fun ingredientsToList(ingredients: String): List<IngredientDto> {
    return json.decodeFromString(ingredients)
}

fun ingredientsToString(ingredients: List<IngredientDto>): String {
    return json.encodeToString(ingredients)
}