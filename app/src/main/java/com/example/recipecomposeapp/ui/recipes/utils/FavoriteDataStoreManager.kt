package com.example.recipecomposeapp.ui.recipes.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlin.collections.emptySet

class FavoriteDataStoreManager(private val context: Context) {

    suspend fun isFavorite(recipeId: Int): Boolean {
        val preferences = context.dataStore.data.first()
        val favoriteIds = preferences[PreferencesKey.FAVORITE_RECIPE_IDS] ?: emptySet()
        return favoriteIds.contains(recipeId.toString())
    }

    suspend fun addFavorite(recipeId: Int) {
        context.dataStore.edit { preferences ->
            val currentFavorite = preferences[PreferencesKey.FAVORITE_RECIPE_IDS] ?: emptySet()
            val updatedFavorite = currentFavorite + recipeId.toString()
            preferences[PreferencesKey.FAVORITE_RECIPE_IDS] = updatedFavorite
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        context.dataStore.edit { preferences ->
            val currentFavorite = preferences[PreferencesKey.FAVORITE_RECIPE_IDS] ?: emptySet()
            val updatedFavorite = currentFavorite - recipeId.toString()
            preferences[PreferencesKey.FAVORITE_RECIPE_IDS] = updatedFavorite
        }
    }
}