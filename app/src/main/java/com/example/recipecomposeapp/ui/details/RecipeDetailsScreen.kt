package com.example.recipecomposeapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.common.ui.ScreenHeader
import com.example.recipecomposeapp.data.repository.getRecipeById
import com.example.recipecomposeapp.ui.details.model.toUiModel
import com.example.recipecomposeapp.core.common.utils.shareRecipe
import com.example.recipecomposeapp.ui.details.components.PortionsSlider
import com.example.recipecomposeapp.ui.recipes.model.toUiModel

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit,
) {

    val context = LocalContext.current
    val recipe = remember(recipeId) {
        getRecipeById(recipeId)?.toUiModel()
    }

    if (recipe == null) {
        Text("Детали рецепта не найдены")
        return
    }

    var currentPortions by rememberSaveable { mutableIntStateOf(recipe.servings) }

    val scaledIngredients = remember(recipe.ingredients, currentPortions) {
        val multiplier = currentPortions.toDouble() / recipe.servings
        recipe.ingredients.map { ingredient ->
            val newAmount = (ingredient.quantity.toDoubleOrNull() ?: 1.0) * multiplier
            ingredient.copy(
                quantity = newAmount.toString()
            )
        }
    }

    val portionsText = pluralStringResource(
        R.plurals.portions_count,
        currentPortions,
        currentPortions
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader(
            imagePainter = recipe.imageUrl,
            contentDescription = recipe.title,
            title = recipe.title,
            showShareButton = true,
            onShareClick = { shareRecipe(context, recipe.id, recipe.title) },
            showFavoriteButton = true,
            onFavoriteToggle = onFavoriteToggle,
            isFavorite = isFavorite
        )
        PortionsSlider(portionsText, currentPortions, onPortionsChange = { newPortion ->
            currentPortions = newPortion
        })
        IngredientList(scaledIngredients)
        InstructionsList(recipe.method)
    }
}