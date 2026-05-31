package com.example.recipecomposeapp.features.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.categories.presentation.model.CategoryUiState
import com.example.recipecomposeapp.features.categories.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(private val repository: RecipesRepository = RecipesRepository()) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val categories = repository.getCategories()
                    .map { currentCategory -> currentCategory.toUiModel() }

                _uiState.update { currentCategory ->
                    currentCategory.copy(categories = categories, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = "Ошибка загрузки категорий: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }
}