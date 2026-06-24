package com.example.recipecomposeapp.core.navigation

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipecomposeapp.Screen
import com.example.recipecomposeapp.core.network.NetworkConfig
import com.example.recipecomposeapp.data.repository.RecipesRepositoryImpl
import com.example.recipecomposeapp.features.categories.ui.CategoriesScreen
import com.example.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import com.example.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import com.example.recipecomposeapp.features.favorites.ui.FavoritesScreen
import com.example.recipecomposeapp.features.recipes.presentation.RecipesViewModel
import com.example.recipecomposeapp.features.recipes.ui.RecipesScreen
import kotlinx.coroutines.delay

@Composable
fun AppNavHost(
    navController: NavHostController,
    deepLinkIntent: Intent?,
    modifier: Modifier = Modifier
) {

    val apiService = NetworkConfig.apiService
    val repository = remember { RecipesRepositoryImpl(apiService) }

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
                repository = repository,
                onCategoryClick = { categoryId, categoryTitle, categoryImageUrl ->
                    navController.navigate(
                        Screen.Recipes.createRoute(
                            categoryId,
                            categoryTitle,
                            categoryImageUrl
                        )
                    )
                }
            )
        }

        composable(
            route = Screen.Recipes.route,
            arguments = Screen.Recipes.arguments
        ) { backStackEntry ->
            val saveStateHandle = backStackEntry.savedStateHandle
            val viewModel = remember(backStackEntry) { RecipesViewModel(
                repository = repository,
                saveStateHandle = saveStateHandle
            ) }
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
            val context = LocalContext.current
            val savedStateHandle = backStackEntry.savedStateHandle
            val viewModel = remember(backStackEntry){ RecipeDetailsViewModel(
                application = context.applicationContext as Application,
                repository = repository,
                savedStateHandle = savedStateHandle
            ) }
            RecipeDetailsScreen(
                viewModel = viewModel
            )
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