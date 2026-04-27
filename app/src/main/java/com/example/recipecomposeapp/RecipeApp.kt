package com.example.recipecomposeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.recipecomposeapp.core.ui.navigation.AppNavHost
import com.example.recipecomposeapp.core.ui.navigation.BottomNavigation
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
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeAppPreview() {
    RecipeApp()
}