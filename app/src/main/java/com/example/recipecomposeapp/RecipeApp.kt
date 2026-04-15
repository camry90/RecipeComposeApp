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
import com.example.recipecomposeapp.core.ui.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.recipes.RecipesScreen
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipeApp() {
    RecipeComposeAppTheme {
        var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }
        var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
        var selectedCategoryTitle by remember { mutableStateOf("") }

        Scaffold(
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = { currentScreen = ScreenId.CATEGORIES },
                    onFavoriteClick = { currentScreen = ScreenId.FAVORITES },
                )
            }
        ) { innerPadding ->
            when (currentScreen) {
                ScreenId.CATEGORIES -> {
                    CategoriesScreen(
                        modifier = Modifier.padding(innerPadding),
                        onCategoryClick = { categoryId, categoryTitle ->
                            selectedCategoryId = categoryId
                            selectedCategoryTitle = categoryTitle
                            currentScreen = ScreenId.RECIPES
                        }
                    )
                }

                ScreenId.FAVORITES -> {
                    FavoritesScreen(modifier = Modifier.padding(innerPadding))
                }

                ScreenId.RECIPES -> {
                    RecipesScreen(
                        categoryId = selectedCategoryId ?: error("Category ID is required"),
                        categoryTitle = selectedCategoryTitle,
                        onRecipeClick = { },
                        modifier = Modifier.padding(innerPadding)
                    )
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