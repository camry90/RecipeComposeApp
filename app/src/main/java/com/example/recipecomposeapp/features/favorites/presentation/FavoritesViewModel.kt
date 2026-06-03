package com.example.recipecomposeapp.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.core.data.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val favoriteManager = FavoriteDataStoreManager(application)
    private val repository: RecipesRepository = RecipesRepository()

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        loadFavoritesRecipes()
    }

    fun loadFavoritesRecipes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                favoriteManager.getFavoriteIdsFlow().map { ids ->
                    ids.mapNotNull { id -> id.toIntOrNull()?.let { repository.getRecipeById(it) } }.map { recipe -> recipe.toUiModel() }
                }.collect { recipes -> _uiState.update { it.copy(favoriteRecipes = recipes, isLoading = false) } }
            } catch (e: Exception) {
                _uiState.update { currentRecipe ->
                    currentRecipe.copy(
                        isLoading = false,
                        error = "Ошибка загрузки рецептов: ${e.message}"
                    )
                }
            }
        }
    }
}