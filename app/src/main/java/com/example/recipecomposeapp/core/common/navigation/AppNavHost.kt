package com.example.recipecomposeapp.core.common.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipecomposeapp.Constants
import com.example.recipecomposeapp.Screen
import com.example.recipecomposeapp.ui.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.details.RecipeDetailsScreen
import com.example.recipecomposeapp.ui.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.recipes.RecipesScreen
import com.example.recipecomposeapp.core.common.data.local.FavoriteDataStoreManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    navController: NavHostController,
    deepLinkIntent: Intent?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val favoriteManager = remember { FavoriteDataStoreManager(context) }
    val coroutineScope = rememberCoroutineScope()
    val favoriteList by favoriteManager
        .getRecipeIdsFlow()
        .collectAsState(initial = emptyList())

    LaunchedEffect(deepLinkIntent) {
        deepLinkIntent?.data?.let { uri ->

            val recipeId: Int? = when (uri.scheme) {
                "recipeapp" -> {
                    if (uri.host == "recipe") {
                        uri.pathSegments[0].toIntOrNull()
                    } else {
                        null
                    }
                }

                "https", "http" -> {
                    if (uri.host == "recipes.androidsprint.ru") {
                        uri.pathSegments[1].toIntOrNull()
                    } else {
                        null
                    }
                }

                else -> {
                    null
                }
            }

            if (recipeId != null) {
                delay(100)
                navController.navigate(Screen.RecipeDetails.createRoute(recipeId))
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Categories.route,
        modifier = modifier,
    ) {

        composable(route = Screen.Categories.route) {
            CategoriesScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate(Screen.Recipes.createRoute(categoryId))
                }
            )
        }

        composable(
            route = Screen.Recipes.route,
            arguments = Screen.Recipes.arguments
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
            RecipesScreen(
                categoryId = categoryId,
                onRecipeClick = { recipeId, recipe ->
                    navController.navigate(Screen.RecipeDetails.createRoute(recipeId))
                }
            )
        }

        composable(
            route = Screen.RecipeDetails.route,
            arguments = Screen.RecipeDetails.arguments
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt(Constants.KEY_RECIPE_OBJECT) ?: 0
            val isFavorite by favoriteManager
                .isFavoriteFlow(recipeId)
                .collectAsState(initial = false)

            RecipeDetailsScreen(
                recipeId,
                onFavoriteToggle = {
                    coroutineScope.launch {
                        if (isFavorite) {
                            favoriteManager.removeFavorite(recipeId)
                        } else {
                            favoriteManager.addFavorite(recipeId)
                        }
                    }
                },
                isFavorite = isFavorite,
            )
        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                recipes = favoriteList,
                onFavoriteClick = { recipeId, recipe ->
                    navController.navigate(Screen.RecipeDetails.createRoute(recipeId))
                }
            )
        }
    }
}