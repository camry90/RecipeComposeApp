package com.example.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.CATEGORIES_COLUMNS
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.data.repository.getCategories
import com.example.recipecomposeapp.ui.categories.model.toUiModel
import com.example.recipecomposeapp.ui.theme.Dimens
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (Int) -> Unit
) {
    val category = getCategories()
    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(R.drawable.categories_header),
            contentDescription = "Categories",
            title = "категории"
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(CATEGORIES_COLUMNS),
            contentPadding = PaddingValues(Dimens.paddingSmall),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall),
            horizontalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
        ) {
            items(category, key = {it.id}) { item ->
                CategoryItem(
                    category = item.toUiModel(),
                    onClick = {
                        onCategoryClick(item.id)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme() {
        CategoriesScreen(
            onCategoryClick = {}
        )
    }
}