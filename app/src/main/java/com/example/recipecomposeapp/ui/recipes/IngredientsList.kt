package com.example.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.ui.recipes.model.IngredientUiModel
import com.example.recipecomposeapp.ui.theme.Dimens
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun IngredientList(
    ingredients: List<IngredientUiModel>,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.paddingMedium),
        shape = RoundedCornerShape(Dimens.shapeRadiusCard),
        elevation = CardDefaults.cardElevation(Dimens.elevationCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        ingredients.forEachIndexed { index, ingredient ->
            IngredientItem(ingredient)
            if (index < ingredients.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = Dimens.paddingMedium)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IngredientListPreview() {
    RecipeComposeAppTheme() {
        IngredientList(
            ingredients = listOf(
                IngredientUiModel(
                    name = "Яйцо",
                    quantity = "2",
                    unitOfMeasure = "штуки"
                ),
                IngredientUiModel(
                    name = "Морковка",
                    quantity = "4",
                    unitOfMeasure = "штуки"
                )
            )
        )
    }
}
