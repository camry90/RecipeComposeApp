package com.example.recipecomposeapp.core.ui.navigation

import android.content.Intent
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipecomposeapp.Constants
import com.example.recipecomposeapp.Screen
import com.example.recipecomposeapp.ui.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.recipes.RecipeDetailsScreen
import com.example.recipecomposeapp.ui.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.recipes.RecipesScreen
import com.example.recipecomposeapp.ui.recipes.utils.FavoritePrefsManager
import kotlinx.coroutines.delay

@Composable
fun AppNavHost(
    navController: NavHostController,
    deepLinkIntent: Intent?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val favoritePrefs = remember { FavoritePrefsManager(context) }

    LaunchedEffect(deepLinkIntent) {
        deepLinkIntent?.data?.let { uri ->
            Log.d("DeepLink", "URI: $uri")
            Log.d("DeepLink", "Scheme: ${uri.scheme}")
            Log.d("DeepLink", "Host: ${uri.host}")
            Log.d("DeepLink", "Path Segments: ${uri.pathSegments}")

            val recipeId: Int? = when(uri.scheme) {
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
            var isFavorite by remember(recipeId) {
                mutableStateOf(favoritePrefs.isFavorite(recipeId))
            }
            RecipeDetailsScreen(
                recipeId,
                onFavoriteToggle = {
                    if (isFavorite) {
                        favoritePrefs.removeFromFavorites(recipeId)
                    } else {
                        favoritePrefs.addToFavorites(recipeId)
                    }
                    isFavorite = !isFavorite
                },
                isFavorite = isFavorite,
            )
        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen()
        }
    }
}