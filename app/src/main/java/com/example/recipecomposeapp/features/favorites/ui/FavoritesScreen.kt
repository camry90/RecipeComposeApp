package com.example.recipecomposeapp.features.favorites.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.data.FavoriteDataStoreManager
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.recipes.ui.RecipeItem
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import com.example.recipecomposeapp.ui.theme.Dimens
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import kotlinx.coroutines.flow.map
import kotlin.collections.emptyList

@Composable
fun FavoritesScreen(
    repository: RecipesRepository = RecipesRepository(),
    favoriteManager: FavoriteDataStoreManager,
    onFavoriteClick: (Int, RecipeUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val favoriteList by remember {
        favoriteManager.getFavoriteIdsFlow().map { ids ->
            ids.mapNotNull { id -> id.toIntOrNull()?.let { repository.getRecipeById(it) } }
        }
    }.collectAsState(initial = emptyList())

    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(R.drawable.favorites_header),
            contentDescription = "Favorites",
            title = "избранное"
        )
        if (favoriteList.isEmpty()) {
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
        } else {
            LazyColumn(modifier = Modifier) {
                items(favoriteList, key = { it.id }) { item ->
                    val recipe = item.toUiModel()
                    RecipeItem(
                        recipe = recipe,
                        onRecipeClick = onFavoriteClick,
                    )
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
