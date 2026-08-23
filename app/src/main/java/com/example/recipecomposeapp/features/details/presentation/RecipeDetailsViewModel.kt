package com.example.recipecomposeapp.features.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.Constants
import com.example.recipecomposeapp.core.data.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val repository: RecipesRepository,
    private val savedStateHandle: SavedStateHandle,
    private val favoriteManager: FavoriteDataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        val recipeId = savedStateHandle.get<Int>(Constants.KEY_RECIPE_OBJECT) ?: 0
        observationFavoriteStatus(recipeId)
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val recipeFlow = repository.getRecipe(recipeId)
            recipeFlow.map { it?.toUiModel() }.collect { recipe ->
                if (recipe != null) {
                    _uiState.update { currentRecipe ->
                        currentRecipe.copy(
                            recipe = recipe,
                            isLoading = false,
                            scaledIngredients = recipe.scaledIngredients(currentRecipe.currentPortions.toDouble())
                        )
                    }
                } else {
                    _uiState.update { currentRecipe ->
                        currentRecipe.copy(
                            recipe = null,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    private fun observationFavoriteStatus(recipeId: Int) {
        viewModelScope.launch {
            favoriteManager.isFavoriteFlow(recipeId).collect { isFavorite ->
                _uiState.update { currentRecipe -> currentRecipe.copy(isFavorite = isFavorite) }
            }
        }
    }

    fun toggleFavorite(recipeId: Int) {
        viewModelScope.launch {
            if (_uiState.value.isFavorite) {
                favoriteManager.removeFavorite(recipeId)
            } else {
                favoriteManager.addFavorite(recipeId)
            }
        }
    }

    fun updatePortions(count: Int) {
        _uiState.update { currentRecipe ->
            val scaled = currentRecipe.recipe?.scaledIngredients(count.toDouble()) ?: emptyList()
            currentRecipe.copy(currentPortions = count, scaledIngredients = scaled)
        }
    }
}
