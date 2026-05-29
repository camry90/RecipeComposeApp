package com.example.recipecomposeapp

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {

    object Categories : Screen("categories")
    object Recipes : Screen("recipes/{categoryId}/{categoryTitle}/{categoryImageUrl}") {
        fun createRoute(categoryId: Int, title: String, imageUrl: String) = "recipes/${categoryId}/${Uri.encode(title)}/${Uri.encode(imageUrl)}"
        val arguments = listOf(
            navArgument("categoryId") { type = NavType.IntType },
            navArgument("categoryTitle") { type = NavType.StringType },
            navArgument("categoryImageUrl") { type = NavType.StringType },
        )
    }

    object Favorites : Screen("favorites")
    object RecipeDetails : Screen("recipe/{${Constants.KEY_RECIPE_OBJECT}}") {
        fun createRoute(recipeId: Int) = "recipe/${recipeId}"
        fun createRecipeDeepLink(recipeId: Int) = "${Constants.DEEP_LINK_BASE_URL}/recipe/$recipeId"
        val arguments = listOf(navArgument(Constants.KEY_RECIPE_OBJECT) { type = NavType.IntType})
    }
}