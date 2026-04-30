package com.example.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.ui.recipes.model.RecipeUiModel

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel
) {
    var currentPortions by remember { mutableIntStateOf(recipe.servings) }

    val scaledIngredients = remember(currentPortions) {
        val multiplier = currentPortions.toDouble() / recipe.servings
        recipe.ingredients.map { ingredient ->
            val newAmount = (ingredient.quantity.toDoubleOrNull() ?: 1.0) * multiplier
            ingredient.copy(
                quantity = newAmount.toString()
            )
        }
    }

//    val portionsText = pluralStringResource(
//        R.plurals.portions_count,
//        currentPortions,
//        currentPortions
//    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader(
            imagePainter = recipe.imageUrl,
            contentDescription = recipe.title,
            title = recipe.title
        )
        PortionsSlider(currentPortions, onPortionsChange = { newPortion ->
            currentPortions = newPortion
        })
        IngredientList(scaledIngredients)
        InstructionsList(recipe.method)
    }
}