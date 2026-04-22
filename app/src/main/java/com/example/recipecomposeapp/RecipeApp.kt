package com.example.recipecomposeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipecomposeapp.core.ui.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.recipes.RecipesScreen
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipeApp() {
    RecipeComposeAppTheme {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = { navController.navigate(Screen.Categories.route) },
                    onFavoriteClick = { navController.navigate(Screen.Favorites.route) },
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Categories.route,
                modifier = Modifier.padding(innerPadding)
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
                    RecipesScreen(categoryId = categoryId) {
                        navController.popBackStack()
                    }

                }
                composable(route = Screen.Favorites.route) {
                    FavoritesScreen()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RecipeAppPreview() {
    RecipeApp()
}