package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.core.utils.ingredientsToList
import com.example.recipecomposeapp.core.utils.ingredientsToString
import com.example.recipecomposeapp.core.utils.methodToList
import com.example.recipecomposeapp.core.utils.methodToString
import com.example.recipecomposeapp.data.database.entity.RecipeEntity
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val imageUrl: String,
    val servings: Int = 4,
)

fun RecipeEntity.toDto() = RecipeDto(
    id = id,
    title = title,
    ingredients = ingredientsToList(ingredients),
    method = methodToList(method),
    imageUrl = imageUrl,
)

fun RecipeDto.toEntity(categoryId: Int) = RecipeEntity(
    id = id,
    categoryId = categoryId,
    title = title,
    imageUrl = imageUrl,
    ingredients = ingredientsToString(ingredients),
    method = methodToString(method)
)