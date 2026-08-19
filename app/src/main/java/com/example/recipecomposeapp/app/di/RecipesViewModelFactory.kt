package com.example.recipecomposeapp.app.di

import androidx.lifecycle.SavedStateHandle
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.recipes.presentation.RecipesViewModel

class RecipesViewModelFactory(
    val savedStateHandle: SavedStateHandle,
    val repository: RecipesRepository
): Factory<RecipesViewModel> {

    override fun create(): RecipesViewModel {
        return RecipesViewModel(repository, savedStateHandle)
    }
}