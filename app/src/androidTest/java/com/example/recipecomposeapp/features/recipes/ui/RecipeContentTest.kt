package com.example.recipecomposeapp.features.recipes.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.recipecomposeapp.features.details.presentation.model.IngredientUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import org.junit.Rule
import org.junit.Test

class RecipeContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showsLoadingState() {

        composeTestRule.setContent {
            RecipeContent(
                uiState = RecipesUiState(
                    isLoading = true,
                ),
                onRecipeClick = { _, _ -> }
            )
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun showsErrorState() {
        composeTestRule.setContent {
            RecipeContent(
                uiState = RecipesUiState(
                    error = "Network error"
                ),
                onRecipeClick = { _, _ -> }
            )
        }

        composeTestRule.onNodeWithTag("error_message").assertIsDisplayed()
    }

    @Test
    fun showsEmptyState() {
        composeTestRule.setContent {
            RecipeContent(
                uiState = RecipesUiState(),
                onRecipeClick = { _, _ -> }
            )
        }

        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
    }

    @Test
    fun displaysRecipeList() {
        composeTestRule.setContent {
            RecipeContent(
                uiState = RecipesUiState(
                    listOf(
                        RecipeUiModel(
                            id = 1,
                            title = "Омлет",
                            ingredients = listOf(
                                IngredientUiModel(
                                    name = "Яйца",
                                    quantity = "2",
                                    unitOfMeasure = "шт"
                                )
                            ),
                            method = listOf(
                                "Разбить яйца",
                                "Пожарить"
                            ),
                            imageUrl = "omelet.jpg",
                            servings = 4,
                        )
                    )
                ),
                onRecipeClick = { _, _ -> }
            )
        }

        composeTestRule.onNodeWithText("ОМЛЕТ").assertIsDisplayed()
    }
}