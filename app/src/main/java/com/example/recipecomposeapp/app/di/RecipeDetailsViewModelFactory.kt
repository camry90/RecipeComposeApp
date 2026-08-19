package com.example.recipecomposeapp.app.di

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel

class RecipeDetailsViewModelFactory(
    val application: Application,
    val savedStateHandle: SavedStateHandle,
    val repository: RecipesRepository
): Factory<RecipeDetailsViewModel> {

    override fun create(): RecipeDetailsViewModel {
        return RecipeDetailsViewModel(application, repository, savedStateHandle)
    }
}