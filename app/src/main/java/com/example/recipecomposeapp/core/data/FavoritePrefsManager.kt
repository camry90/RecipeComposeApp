package com.example.recipecomposeapp.core.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class FavoritePrefsManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("recipe_app_prefs", Context.MODE_PRIVATE)

    fun isFavorite(recipeId: Int): Boolean {
        val favoriteRecipeId = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet()) ?: emptySet()
        return recipeId.toString() in favoriteRecipeId
    }

    fun addToFavorites(recipeId: Int) {
        val currentFavorites = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())
        val updatedFavorites = currentFavorites?.toMutableSet() ?: mutableSetOf()
        updatedFavorites.add(recipeId.toString())
        sharedPreferences.edit {
            putStringSet("favorite_recipe_ids", updatedFavorites)
        }
    }

    fun removeFromFavorites(recipeId: Int) {
        val currentFavorites = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())
        val updatedFavorites = currentFavorites?.toMutableSet() ?: mutableSetOf()
        updatedFavorites.remove(recipeId.toString())
        sharedPreferences.edit {
            putStringSet("favorite_recipe_ids", updatedFavorites)
        }
    }

    fun getAllFavorites(): Set<String> {
        val allFavorites = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet()) ?: emptySet()
        return allFavorites
    }
}