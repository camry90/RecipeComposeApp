package com.example.recipecomposeapp.core.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipecomposeapp.Constants
import com.example.recipecomposeapp.Screen
import com.example.recipecomposeapp.ui.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.details.RecipeDetailsScreen
import com.example.recipecomposeapp.ui.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.recipes.RecipesScreen
import com.example.recipecomposeapp.ui.recipes.model.RecipeUiModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
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
                onRecipeClick = { categoryId, recipe ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = Constants.KEY_RECIPE_OBJECT,
                        value = recipe
                    )
                    navController.navigate(Screen.RecipeDetails.route)
                }
            )
        }

        composable(route = Screen.RecipeDetails.route) {
            val recipe = navController
                .previousBackStackEntry?.savedStateHandle?.get<RecipeUiModel>(Constants.KEY_RECIPE_OBJECT)
            recipe?.let {
                RecipeDetailsScreen(recipe)
            } ?: Text("Детали не найдены")
        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen()
        }
    }
}