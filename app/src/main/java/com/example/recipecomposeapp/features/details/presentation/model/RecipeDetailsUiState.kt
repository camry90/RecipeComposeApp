package com.example.recipecomposeapp.features.details.presentation.model

import com.example.recipecomposeapp.data.model.RecipeDto
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel

data class RecipeDetailsUiState(
    val recipe: RecipeUiModel? = null,
    val servingsCount: Int = 4,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)