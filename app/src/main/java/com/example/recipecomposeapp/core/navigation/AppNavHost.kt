package com.example.recipecomposeapp.core.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipecomposeapp.Constants
import com.example.recipecomposeapp.Screen
import com.example.recipecomposeapp.features.categories.ui.CategoriesScreen
import com.example.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import com.example.recipecomposeapp.features.favorites.ui.FavoritesScreen
import com.example.recipecomposeapp.features.recipes.ui.RecipesScreen
import com.example.recipecomposeapp.core.data.FavoriteDataStoreManager
import com.example.recipecomposeapp.features.recipes.presentation.RecipesViewModel
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
                onCategoryClick = { categoryId, categoryTitle, categoryImageUrl ->
                    navController.navigate(Screen.Recipes.createRoute(categoryId, categoryTitle, categoryImageUrl))
                }
            )
        }

        composable(
            route = Screen.Recipes.route,
            arguments = Screen.Recipes.arguments
        ) { backStackEntry ->
            val viewModel: RecipesViewModel = viewModel(
                factory = viewModelFactory {
                   initializer {
                       RecipesViewModel(saveStateHandle = backStackEntry.savedStateHandle)
                   }
                }
            )
            RecipesScreen(
                viewModel = viewModel,
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
            RecipeDetailsScreen(recipeId)
        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                onFavoriteClick = { recipeId, recipe ->
                    navController.navigate(Screen.RecipeDetails.createRoute(recipeId))
                }
            )
        }
    }
}