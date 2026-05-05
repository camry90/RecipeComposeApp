package com.example.recipecomposeapp

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {

    object Categories : Screen("categories")
    object Recipes : Screen("recipes/{categoryId}") {
        fun createRoute(categoryId: Int) = "recipes/${categoryId}"
        val arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
    }

    object Favorites : Screen("favorites")
    object RecipeDetails : Screen("recipe-details/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipe-details/${recipeId}"
        val arguments = listOf(navArgument("recipeId") { type = NavType.IntType})
    }
}