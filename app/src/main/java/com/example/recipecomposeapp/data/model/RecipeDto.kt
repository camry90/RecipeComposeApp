package com.example.recipecomposeapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: String,
    val imageUrl: String,
    val servings: Int = 4,
)