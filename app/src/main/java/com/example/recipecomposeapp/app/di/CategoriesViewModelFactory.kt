package com.example.recipecomposeapp.app.di

import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.categories.presentation.CategoriesViewModel

class CategoriesViewModelFactory(
    val repository: RecipesRepository
): Factory<CategoriesViewModel> {

    override fun create(): CategoriesViewModel {
        return CategoriesViewModel(repository)
    }
}