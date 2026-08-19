package com.example.recipecomposeapp.core.navigation

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipecomposeapp.Screen
import com.example.recipecomposeapp.app.di.FavoritesViewModelFactory
import com.example.recipecomposeapp.app.di.RecipeApplication
import com.example.recipecomposeapp.app.di.RecipeDetailsViewModelFactory
import com.example.recipecomposeapp.app.di.RecipesViewModelFactory
import com.example.recipecomposeapp.data.network.NetworkConfig
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.repository.RecipesRepositoryImpl
import com.example.recipecomposeapp.features.categories.ui.CategoriesScreen
import com.example.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import com.example.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import com.example.recipecomposeapp.features.favorites.presentation.FavoritesViewModel
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
    val appContainer = (LocalContext.current.applicationContext as RecipeApplication).appContainer
    val context = LocalContext.current

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

            val savedStateHandle = remember(backStackEntry) {
                SavedStateHandle().apply {
                    backStackEntry.arguments?.let { bundle ->
                        bundle.keySet().forEach { key -> set(key, bundle.get(key)) }
                    }
                }
            }
            val viewModel = remember {
                RecipesViewModelFactory(
                    savedStateHandle,
                    appContainer.recipesRepository
                ).create()
            }
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
            val appContainer = (LocalContext.current.applicationContext as RecipeApplication).appContainer
            val context = LocalContext.current

            val savedStateHandle = remember(backStackEntry) {
                SavedStateHandle().apply {
                    backStackEntry.arguments?.let { bundle ->
                        bundle.keySet().forEach { key -> set(key, bundle.get(key)) }
                    }
                }
            }
            val viewModel = remember {
                RecipeDetailsViewModelFactory(
                    application = context.applicationContext as Application,
                    savedStateHandle = savedStateHandle,
                    repository = appContainer.recipesRepository
                ).create()
            }
            RecipeDetailsScreen(
                viewModel = viewModel
            )

        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                onFavoriteClick = { recipeId, recipe ->
                    navController.navigate(Screen.RecipeDetails.createRoute(recipeId))
                },
            )

        }
    }
}