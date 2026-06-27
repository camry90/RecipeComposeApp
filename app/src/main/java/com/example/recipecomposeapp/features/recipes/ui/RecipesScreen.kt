package com.example.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.recipes.presentation.RecipesViewModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.example.recipecomposeapp.ui.theme.Dimens
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    viewModel: RecipesViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        ScreenHeader(
            imagePainter = uiState.categoryImageUrl,
            contentDescription = "Recipes",
            title = uiState.categoryTitle
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

            uiState.isEmpty -> {
                Text(
                    text = "В этой категории пока нет рецептов",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(Dimens.paddingMedium),
                    verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall),
                ) {
                    items(uiState.recipes, key = { it.id }) { recipe ->
                        RecipeItem(
                            recipe = recipe,
                            onRecipeClick = { recipeId, recipeUiModel ->
                                onRecipeClick(recipeId, recipeUiModel)
                            }
                        )
                    }
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview() {
    RecipeComposeAppTheme {
//        RecipesScreen()
    }
}