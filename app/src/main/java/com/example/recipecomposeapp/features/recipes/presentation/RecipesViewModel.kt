package com.example.recipecomposeapp.features.recipes.presentation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipesViewModel(
    private val repository: RecipesRepository,
    private val saveStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val categoryId = saveStateHandle.get<Int>("categoryId") ?: 0
            val categoryTitle = Uri.decode(saveStateHandle.get<String>("categoryTitle") ?: "")
            val categoryImageUrl = Uri.decode(saveStateHandle.get<String>("categoryImageUrl") ?: "")

            try {
                val recipes = repository.getRecipesByCategory(categoryId)
                    .map { currentRecipe -> currentRecipe.toUiModel() }

                _uiState.update { currentRecipes ->
                    currentRecipes.copy(
                        recipes = recipes,
                        categoryTitle = categoryTitle,
                        categoryImageUrl = categoryImageUrl,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentRecipes ->
                    currentRecipes.copy(
                        isLoading = false,
                        error = "Ошибка загрузки рецептов: ${e.message}"
                    )
                }
            }
        }
    }
}