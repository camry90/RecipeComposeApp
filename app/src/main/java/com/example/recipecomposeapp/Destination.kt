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
    object RecipeDetails : Screen("recipe/{${Constants.KEY_RECIPE_OBJECT}}") {
        fun createRoute(recipeId: Int) = "recipe/${recipeId}"
        fun createRecipeDeepLink(recipeId: Int) = "${Constants.DEEP_LINK_BASE_URL}/recipe/$recipeId"
        val arguments = listOf(navArgument(Constants.KEY_RECIPE_OBJECT) { type = NavType.IntType})
    }
}