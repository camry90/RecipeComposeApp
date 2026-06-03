package com.example.recipecomposeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.core.data.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val repository: RecipesRepository = RecipesRepository()
    private val favoriteManager = FavoriteDataStoreManager(application)
    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()
    private var currentRecipeId: Int = 0

    fun initializeRecipe(recipeId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            currentRecipeId = recipeId
            observationFavoriteStatus()
            try {
                val recipe = repository.getRecipeById(recipeId)
                    ?.toUiModel()
                if (recipe != null) {
                    _uiState.update { currentRecipe ->
                        currentRecipe.copy(
                            recipe = recipe,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = "Ошибка загрузки страницы рецепта: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun observationFavoriteStatus() {
        viewModelScope.launch {
            favoriteManager.isFavoriteFlow(currentRecipeId).collect { isFavorite ->
                _uiState.update { currentRecipe -> currentRecipe.copy(isFavorite = isFavorite) }
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            if (_uiState.value.isFavorite) {
                favoriteManager.removeFavorite(currentRecipeId)
            } else {
                favoriteManager.addFavorite(currentRecipeId)
            }
        }
    }

    fun updatePortions(count: Int) {
        _uiState.update { currentRecipe -> currentRecipe.copy(servingsCount = count) }
    }
}