package com.example.recipecomposeapp.core.common.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class FavoriteDataStoreManager(private val context: Context) {

    private fun getFavoritesIdsFlow(): Flow<Set<String>> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKey.FAVORITE_RECIPE_IDS] ?: emptySet()
        }
    }

    fun isFavoriteFlow(recipeId: Int): Flow<Boolean> {
        return getFavoritesIdsFlow().map { favoriteIds ->
            favoriteIds.contains(recipeId.toString())
        }
    }

    fun getFavoriteCountFlow(): Flow<Int> {
        return getFavoritesIdsFlow().map { favoriteIds ->
            favoriteIds.size
        }
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