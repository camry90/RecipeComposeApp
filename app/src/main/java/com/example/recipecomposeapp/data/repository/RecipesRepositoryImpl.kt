package com.example.recipecomposeapp.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.recipecomposeapp.data.network.api.RecipeApiService
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto
import com.example.recipecomposeapp.data.model.toDto
import com.example.recipecomposeapp.data.model.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RecipesRepositoryImpl(
    private val apiService: RecipeApiService,
    private val database: RecipesDatabase,
) : RecipesRepository {

    private val categoryDao = database.categoryDao()
    private val recipeDao = database.recipeDao()

    override fun getCategories(): Flow<List<CategoryDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fresh = apiService.getCategories()
                categoryDao.insertCategories(fresh.map { it.toEntity() })
                Log.d(TAG, "Обновлено ${fresh.size} категорий")
            } catch (e: Exception) {
                Log.e("RecipesRepositoryImpl", "Ошибка загрузки категорий: ${e.message}")
            }
        }

        return categoryDao.getAllCategories()
            .map { entities -> entities.map { it.toDto() } }
    }

    override fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fresh = apiService.getRecipesByCategory(categoryId)
                recipeDao.insertRecipes(fresh.map { it.toEntity(categoryId) })
                Log.d(TAG, "Обновлено ${fresh.size} рецептов")
            } catch (e: Exception) {
                Log.e(
                    "RecipesRepositoryImpl",
                    "Ошибка загрузки рецептов категории по id: $categoryId по ошибке ${e.message}"
                )
            }
        }

        return recipeDao.getRecipesByCategoryId(categoryId)
            .map { entities -> entities.map { it.toDto() } }
    }

    override fun getRecipesByIds(ids: List<Int>): Flow<List<RecipeDto>> {
        return if (ids.isEmpty()) {
            flowOf(emptyList())
        } else {
            recipeDao.getRecipesByIds(ids)
                .map { entities -> entities.map { it.toDto() } }
        }
    }

    override fun getRecipe(recipeId: Int): Flow<RecipeDto?> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fresh = apiService.getRecipe(recipeId)
                val existing = recipeDao.getRecipeById(recipeId).first()
                if (existing != null) {
                    recipeDao.insertRecipes(listOf(fresh.toEntity(existing.categoryId)))
                } else {
                    Log.d(
                        "RecipesRepositoryImpl",
                        "Рецепт $recipeId не найден в кеше, синхронизация пропущена"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "RecipesRepositoryImpl",
                    "Ошибка загрузки рецепта по id: $recipeId по ошибке ${e.message}"
                )
            }
        }

        return recipeDao.getRecipeById(recipeId)
            .map { entity -> entity?.toDto() }
    }
}