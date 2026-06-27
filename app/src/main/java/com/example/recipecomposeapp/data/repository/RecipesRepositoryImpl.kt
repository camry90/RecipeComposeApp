package com.example.recipecomposeapp.data.repository

import android.util.Log
import com.example.recipecomposeapp.core.network.api.RecipeApiService
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesRepositoryImpl(
    private val apiService: RecipeApiService,
): RecipesRepository {

    override suspend fun getCategories(): List<CategoryDto> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getCategories()
            } catch(e: Exception) {
                Log.e("RecipesRepositoryImpl", "Ошибка загрузки категорий: ${e.message}" )
                emptyList()
            }
        }
    }

    override suspend fun getRecipesByCategory(categoryId: Int): List<RecipeDto> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getRecipesByCategory(categoryId)
            } catch (e: Exception) {
                Log.e("RecipesRepositoryImpl", "Ошибка загрузки рецептов категории по id: $categoryId по ошибке ${e.message}")
                emptyList()
            }
        }
    }

    override suspend fun getRecipe(recipeId: Int): RecipeDto {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getRecipe(recipeId)
            } catch (e: Exception) {
                Log.e("RecipesRepositoryImpl", "Ошибка загрузки рецепта по id: $recipeId по ошибке ${e.message}")
                throw e
            }
        }
    }
}