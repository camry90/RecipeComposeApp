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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.core.utils.shareRecipe
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import com.example.recipecomposeapp.features.details.ui.components.PortionsSlider
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel

@Composable
fun RecipeDetailsScreen(
    recipeId: Int
) {
    val context = LocalContext.current
    val viewModel: RecipeDetailsViewModel = viewModel()
    LaunchedEffect(recipeId) {
        viewModel.initializeRecipe(recipeId)
    }
    val uiState by viewModel.uiState.collectAsState()
    val portionsText = pluralStringResource(
        R.plurals.portions_count,
        uiState.servingsCount,
        uiState.servingsCount
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
            onFavoriteToggle = { viewModel.toggleFavorite() },
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
                PortionsSlider(portionsText, uiState.servingsCount, onPortionsChange = { newPortion ->
                    viewModel.updatePortions(newPortion)
                })
                uiState.recipe?.scaledIngredients(uiState.servingsCount.toDouble())?.let { IngredientList(it) }
                InstructionsList(uiState.recipe?.method ?: "")
            }
        }
    }
}