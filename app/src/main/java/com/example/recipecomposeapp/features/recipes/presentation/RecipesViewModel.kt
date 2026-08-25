package com.example.recipecomposeapp.features.recipes.presentation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.core.utils.UriDecoder
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
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
class RecipesViewModel @Inject constructor(
    private val repository: RecipesRepository,
    private val saveStateHandle: SavedStateHandle,
    private val uriDecoder: UriDecoder,
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
            val categoryTitle = uriDecoder.decode(saveStateHandle.get<String>("categoryTitle") ?: "")
            val categoryImageUrl = uriDecoder.decode(saveStateHandle.get<String>("categoryImageUrl") ?: "")

            try {
                repository.getRecipesByCategory(categoryId)
                    .map { currentRecipes -> currentRecipes.map { it.toUiModel() } }
                    .collect { currentRecipe ->
                        _uiState.update {
                            it.copy(
                                recipes = currentRecipe,
                                categoryTitle = categoryTitle,
                                categoryImageUrl = categoryImageUrl,
                                isLoading = false
                            )
                        }
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