package com.example.recipecomposeapp.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.core.data.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    val repository: RecipesRepository,
    val favoriteManager: FavoriteDataStoreManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        loadFavoritesRecipes()
    }

    fun loadFavoritesRecipes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                favoriteManager.getFavoriteIdsFlow()
                    .flatMapLatest { ids -> repository.getRecipesByIds(ids.mapNotNull { id -> id.toIntOrNull() }) }
                    .map { recipesDto -> recipesDto.map { recipeDto -> recipeDto.toUiModel() } }
                    .collect { recipes ->
                        _uiState.update { it.copy(favoriteRecipes = recipes, isLoading = false) }
                    }
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