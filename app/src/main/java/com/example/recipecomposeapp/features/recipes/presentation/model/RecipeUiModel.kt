package com.example.recipecomposeapp.features.recipes.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.recipecomposeapp.Constants
import com.example.recipecomposeapp.data.model.RecipeDto
import com.example.recipecomposeapp.features.details.presentation.model.IngredientUiModel
import com.example.recipecomposeapp.features.details.presentation.model.toUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientUiModel>,
    val method: String,
    val imageUrl: String,
    val servings: Int,
) : Parcelable

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    ingredients = ingredients.map { it.toUiModel() },
    method = method,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl,
    servings = servings
)