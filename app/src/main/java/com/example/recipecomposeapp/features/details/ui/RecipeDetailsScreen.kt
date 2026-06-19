package com.example.recipecomposeapp.features.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.core.utils.methodToString
import com.example.recipecomposeapp.core.utils.shareRecipe
import com.example.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import com.example.recipecomposeapp.features.details.ui.components.PortionsSlider

@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailsViewModel
) {
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()
    val portionsText = pluralStringResource(
        R.plurals.portions_count,
        uiState.currentPortions,
        uiState.currentPortions
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader(
            imagePainter = uiState.recipe?.imageUrl ?: "",
            contentDescription = uiState.recipe?.title ?: "",
            title = uiState.recipe?.title ?: "",
            showShareButton = true,
            onShareClick = { shareRecipe(context, uiState.recipe?.id ?: 0, uiState.recipe?.title ?: "") },
            showFavoriteButton = true,
            onFavoriteToggle = { viewModel.toggleFavorite(uiState.recipe?.id ?: 0) },
            isFavorite = uiState.isFavorite
        )
    when {
        uiState.isLoading -> {
            CircularProgressIndicator()
        }
        uiState.error != null -> {
            Text(
                text = "${uiState.error}"
            )
        }
        else -> {
                PortionsSlider(portionsText, uiState.currentPortions, onPortionsChange = { newPortion ->
                    viewModel.updatePortions(newPortion)
                })
                IngredientList(uiState.scaledIngredients)
                InstructionsList((methodToString(uiState.recipe?.method)))
            }
        }
    }
}