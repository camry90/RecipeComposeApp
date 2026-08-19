package com.example.recipecomposeapp.app.di

import android.app.Application
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.favorites.presentation.FavoritesViewModel

class FavoritesViewModelFactory(
    val application: Application,
    val repository: RecipesRepository
): Factory<FavoritesViewModel> {

    override fun create(): FavoritesViewModel {
        return FavoritesViewModel(application, repository)
    }
}