package com.example.recipecomposeapp.features.favorites.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.favorites.presentation.FavoritesViewModel
import com.example.recipecomposeapp.features.recipes.ui.RecipeItem
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.example.recipecomposeapp.ui.theme.Dimens
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun FavoritesScreen(
    onFavoriteClick: (Int, RecipeUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: FavoritesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()


    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(R.drawable.favorites_header),
            contentDescription = "Favorites",
            title = "избранное"
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = Dimens.paddingMedium),
                ) {
                    Text(
                        text = "Добавьте рецепты в избранное".uppercase(),
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            }

            else -> {
                LazyColumn(modifier = Modifier) {
                    items(uiState.favoriteRecipes, key = { it.id }) { item ->
                        RecipeItem(
                            recipe = item,
                            onRecipeClick = onFavoriteClick,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    RecipeComposeAppTheme() {
//        FavoritesScreen()
    }
}
